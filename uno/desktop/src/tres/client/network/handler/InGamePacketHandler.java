package tres.client.network.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tres.network.packet.ClientInfo;
import com.tres.network.packet.PlayerInfo;
import com.tres.network.packet.cipher.CommonKeyNetworkCipher;
import com.tres.network.packet.cipher.PublicKeyNetworkCipher;
import com.tres.network.packet.protocol.*;
import com.tres.network.packet.protocol.types.CardTransaction;
import com.tres.network.packet.protocol.types.JwtUtils;
import com.tres.network.packet.protocol.types.PacketHandlingException;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.container.ContainerIds;
import com.tres.network.uno.container.NetworkCardContainer;
import com.tres.utils.Colors;
import tres.client.Client;
import tres.client.ClientSession;
import tres.client.event.LoginCompleteEvent;
import tres.client.event.game.PlayerJoinedToGameEvent;
import tres.client.event.game.PlayerLeftGameEvent;
import tres.client.network.FileChunkReceiver;
import tres.client.uno.ClientPlayer;
import tres.client.uno.ViewPlayer;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class InGamePacketHandler extends BasePacketHandler {

	public InGamePacketHandler(Client client, ClientSession session) {
		super(client, session);
	}

	@Override
	public void handleText(TextPacket packet) {
		this.client.getLogger().info(String.format("<%s> %s", packet.sourceName, packet.message));
	}

	@Override
	public void handleDisconnect(DisconnectPacket packet) {
		this.client.getLogger().warn("Disconnected by server. Reason: " + packet.reason);
		this.client.close();
	}

	@Override
	public void handleAliveSignal(AliveSignalPacket packet) {
		AliveSignalPacket response = new AliveSignalPacket();
		response.isig = packet.isig + 1;

		this.session.sendDataPacket(response);
	}

	@Override
	public void handleLoginStatus(LoginStatusPacket packet) {
		if (packet.status == LoginStatusPacket.REQUEST_PROTOCOL) {
			LoginPacket response = new LoginPacket();
			response.protocol = ProtocolIds.PROTOCOL;
			response.version = ProtocolIds.VERSION;
			response.jwtToken = this.session.getClient().getLoginJWT();

			this.session.sendDataPacket(response);
		} else if (packet.status == LoginStatusPacket.REQUEST_CLIENT_INFO) {
			PlayerInfoRequestPacket request = new PlayerInfoRequestPacket();
			request.name = "Requested Name";

			this.session.sendDataPacket(request);

			ClientInfoPacket response = new ClientInfoPacket();
			response.hasComputerInfo = !this.session.getClient().isAnonymousMode();
			if (response.hasComputerInfo) {
				response.remoteAddress = this.session.getClient().getSocket().getLocalAddress().toString();
			}
			response.info = new ClientInfo("RequestedName");

			this.session.sendDataPacket(response);
		}
	}

	@Override
	public void handlePlayerInitialized(PlayerInitializedPacket packet) {
		this.session.getLogger().info("Player initialized as server! name: " + packet.name + " runtimeId: " + packet.runtimeId);

		this.session.createPlayer(packet.runtimeId, new PlayerInfo(packet.name));

		this.client.getEventEmitter().emit(new LoginCompleteEvent());
	}

	protected void checkGameLevelDetected() throws PacketHandlingException {
		if (this.session.getActions().getLatestGameLevel() == null) {
			throw new PacketHandlingException("game level not detected");
		}
	}

	@Override
	public void handleGameEvent(GameEventPacket packet) {
		this.session.getLogger().info("Game event: " + packet.event + " player: " + packet.playerRuntimeId + " game: " + packet.gameId);


		if (packet.event == GameEventPacket.PLAYER_JOIN && packet.playerRuntimeId > -1) {
			this.checkGameLevelDetected();

			if (packet.playerRuntimeId == this.session.getPlayer().getId()) {
				this.session.getPlayer().onJoinGame(packet.gameId);
			}

			this.client.getEventEmitter().emit(new PlayerJoinedToGameEvent(packet.gameId, packet.playerRuntimeId));
		}

		if (packet.event == GameEventPacket.PLAYER_LEFT && packet.playerRuntimeId > -1) {

			if (packet.playerRuntimeId == this.session.getPlayer().getId()) {
				this.session.getPlayer().onLeftGame();
			}

			this.client.getEventEmitter().emit(new PlayerLeftGameEvent(packet.gameId, packet.playerRuntimeId));
		}

		if (packet.event == GameEventPacket.TURN_START && packet.playerRuntimeId > -1) {
			if (packet.playerRuntimeId == this.session.getPlayer().getId()) {
				this.session.getPlayer().getInGameData().createTurnData();
			}
		}

		if (packet.event == GameEventPacket.TURN_END && packet.playerRuntimeId > -1) {
			if (packet.playerRuntimeId == this.session.getPlayer().getId()) {
				this.session.getPlayer().getInGameData().destroyTurnData();
			}
		}
	}

	@Override
	public void handleGameLevel(GameLevelPacket packet) {
	}

	@Override
	public void handleServerToClientHandshake(ServerToClientHandshakePacket packet) {
		if (this.session.getPacketSender().getCipher() != null) {
			this.session.disconnect("Bad Packet: already encrypted");
			return;
		}

		this.session.getLogger().info("Encryption start handshake received");


		//todo: rework around ClientSession cipher

		SecretKey key = this.session.generateCipherKey();

		CommonKeyNetworkCipher cipher = new CommonKeyNetworkCipher(key);
		this.session.getLogger().info("Processing handshake JWT...");
		PublicKey publicKey = this.processServerToClientHandshakeJWT(packet.jwtToken);

		this.session.getLogger().info("Server Public Key: " + Colors.wrap(Base64.getEncoder().encodeToString(publicKey.getEncoded()), Colors.YELLOW_BRIGHT));
		this.session.getLogger().info("Secret(Symmetric) Key: " + Colors.wrap(Base64.getEncoder().encodeToString(key.getEncoded()), Colors.MAGENTA));
		this.session.getLogger().warn(Colors.wrap("Do not share Secret(Symmetric) key!", Colors.RED_BACKGROUND));

		PublicKeyNetworkCipher beforeCipher = new PublicKeyNetworkCipher(publicKey, null);
		this.session.getActions().cipherHandshake(key, beforeCipher);

		this.session.getLogger().info(Colors.wrap("Public-key encrypted handshake sent", Colors.CYAN_BRIGHT));

		this.session.setCipher(cipher);
		this.session.setCipherKey(key);

		this.session.getLogger().info("Payload encryption started");
		// fixme: no guarantee (cipher, cipher key) are the same


	}

	@Override
	public void handlePlayerGameAction(PlayerGameActionPacket packet) {

	}

	@Override
	public void handleGameResult(GameResultPacket packet) {

	}

	@Override
	public void handleCardTransaction(CardTransactionPacket packet) {
		if (this.session.getPlayer() == null) {
			return;
		}

		if (this.session.getPlayer().getInGameData() == null) {
			return;
		}

		ClientPlayer player = this.session.getPlayer();

		NetworkCardContainer container;

		ViewPlayer viewPlayer = this.session.getViewPlayers().get(packet.transaction.playerRuntimeId);

		if (viewPlayer == null && packet.transaction.container == ContainerIds.PLAYER) {
			this.session.getLogger().info("Unknown player: " + packet.transaction.playerRuntimeId);
			return;
		}

		if (packet.transaction.container == ContainerIds.PLAYER) {
			container = viewPlayer.getCards();
		} else if (packet.transaction.container == ContainerIds.CARD_STACK) {
			container = this.session.getPlayer().getInGameData().getGame().getStack();
		} else {
			this.session.getLogger().info("Unknown container: " + packet.transaction.container);
			return;
		}

		this.session.getLogger().info("Container: " + packet.transaction.container);


		CardTransaction trans = packet.transaction;

		if (trans.type == CardTransaction.TYPE_ADD) {
			container.addAll(trans.cards);
			this.session.getLogger().info("Card transact: Added " + trans.cards.size() + " cards, reason: " + trans.reason + ", player: " + trans.playerRuntimeId);
		} else if (trans.type == CardTransaction.TYPE_REMOVE) {
			for (NetworkCard card : trans.cards) {
				Integer slot = container.getSlotByRuntimeId(card.runtimeId);
				if (slot != null) {
					container.remove(slot);
				}
			}
			this.session.getLogger().info("Card transact: Removed " + trans.cards.size() + " cards, reason: " + trans.reason + ", player: " + trans.playerRuntimeId);
		}
	}

	@Override
	public void handleCardList(CardListPacket packet) {
		if (this.session.getPlayer() == null) {
			return;
		}

		if (this.session.getPlayer().getInGameData() == null) {
			return;
		}

		NetworkCardContainer container;

		if (packet.container == ContainerIds.PLAYER) {
			container = this.session.getPlayer().getCards();
		} else if (packet.container == ContainerIds.CARD_STACK) {
			container = this.session.getPlayer().getInGameData().getGame().getStack();
		} else {
			this.session.getLogger().info("Unknown container: " + packet.container);
			return;
		}

		container.clear();
	}

	@Override
	public void handleFileChunkData(FileChunkDataPacket packet) {

		FileChunkReceiver receiver = this.session.getFileChunkReceiver();

		if (receiver != null) {
			receiver.receive(packet.chunkIndex, packet.chunkPayload);

			float completeSize = (float) (receiver.getInfo().maxChunkSize * receiver.getCompleteChunkCount());
			float percentage = completeSize / (receiver.getInfo().chunkCount * receiver.getInfo().maxChunkSize);
			float kbps = (completeSize / ((float) receiver.getTimeElapsed() / 1000)) / 1024;


			this.session.getLogger().info(
					String.format("Received file chunk data %s(%s/s) (index: %s)", Colors.wrap((percentage * 100) + "%", Colors.YELLOW_BRIGHT), Colors.wrap(kbps + "kB", Colors.BLUE_BRIGHT), packet.chunkIndex)
			);
		}
	}

	@Override
	public void handleFileChunkStream(FileChunkStreamPacket packet) {
		FileChunkStreamResponsePacket response = new FileChunkStreamResponsePacket();
		response.background = packet.requestBackground;
		response.status = FileChunkStreamResponsePacket.STATUS_ACCEPTED;

		if (this.session.getFileChunkReceiver() != null) {
			response.status = FileChunkStreamResponsePacket.STATUS_REFUSED;
		}

		if (response.status == FileChunkStreamResponsePacket.STATUS_ACCEPTED) {
			this.session.getLogger().info(
					String.format(
							"Accepted file stream request. (chunkSize: %d, chunkCount: %d, digest: %s, identifier: %s)",
							packet.info.maxChunkSize,
							packet.info.chunkCount,
							packet.info.digest,
							packet.info.identifier
					)
			);

			this.session.createFileChunkReceiver(packet.info, packet.requestBackground);
		}

		this.session.sendDataPacket(response);
	}

	@Override
	public void handleAddPlayer(AddPlayerPacket packet) {
		if (this.session.getPlayer() != null && this.session.getPlayer().getId() == packet.runtimeId) {
			return;
		}

		ViewPlayer viewPlayer = new ViewPlayer(packet.runtimeId, packet.info);
		this.session.getViewPlayers().put(packet.runtimeId, viewPlayer);

		this.session.getLogger().info(String.format("Added player: %s (%d)", packet.info.getUsername(), packet.runtimeId));
	}

	@Override
	public void handleRemovePlayer(RemovePlayerPacket packet) {
		this.session.getViewPlayers().remove(packet.runtimeId);
		this.session.getLogger().info(String.format("Removed player: %d", packet.runtimeId));

	}

	@Override
	public void handleAvailableGames(AvailableGamesPacket packet) {

	}

	protected PublicKey processServerToClientHandshakeJWT(String token) {
		JWTVerifier verifier = JWT.require(Algorithm.none())
				.withAudience("client")
				.withSubject("encryption")
				.withIssuer(JwtUtils.issuer)
				.withClaimPresence("key")
				.withClaimPresence("algorithm")
				.build();

		DecodedJWT jwt;
		try {
			jwt = verifier.verify(token);
		} catch (JWTVerificationException e) {
			throw new PacketHandlingException(e);
		}

		String publicKey = jwt.getClaim("key").asString();
		String algorithm = jwt.getClaim("algorithm").asString();
		byte[] encoded = publicKey.getBytes(StandardCharsets.UTF_8);
		byte[] keySeq = Base64.getDecoder().decode(encoded);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(keySeq);

		PublicKey key;
		try {
			key = KeyFactory.getInstance(algorithm).generatePublic(spec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			this.session.disconnect("Bad Packet: KeyFactory");
			throw new RuntimeException(e);
		}

		return key;
	}
}
