package tres.client;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.cipher.NetworkCipher;
import com.tres.network.packet.protocol.*;
import com.tres.network.packet.protocol.types.AvailableGameInfo;
import com.tres.network.packet.protocol.types.PlayerAction;
import com.tres.promise.Promise;
import com.tres.utils.EventLinks;
import org.jetbrains.annotations.Nullable;
import tres.client.event.packet.DataPacketReceiveEvent;
import tres.client.network.PacketResponsePromise;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

public class ClientNetworkActions {

	private final EventLinks eventLinks;

	protected ClientSession session;

	protected ArrayList<AvailableGameInfo> availableGames;

	protected int lastFetchAvailableGames;

	protected PacketResponsePromise<RequestAvailableGamesPacket, AvailableGamesPacket> availableGamePromise;

	protected GameLevelPacket latestGameLevel;

	public ClientNetworkActions(ClientSession session) {
		this.session = session;
		this.eventLinks = new EventLinks(this.session.getClient().getEventEmitter());
		this.eventLinks.on(this.session.getClient().getEventEmitter().on(DataPacketReceiveEvent.class, (channel, event) -> {
			this.onDataPacketReceive(event);
		}));

		this.availableGames = new ArrayList<>();
		this.availableGamePromise = null;
		this.lastFetchAvailableGames = -200;
		this.latestGameLevel = null;
	}

	public @Nullable GameLevelPacket getLatestGameLevel() {
		return latestGameLevel;
	}

	public void setLatestGameLevel(GameLevelPacket latestGameLevel) {
		this.latestGameLevel = latestGameLevel;
	}

	public void close() {
		this.eventLinks.offAll();
	}

	private void onDataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket packet = event.getPacket();

		if (packet instanceof AvailableGamesPacket) {
			this.availableGames.clear();
			this.availableGames.addAll(((AvailableGamesPacket) packet).games);
		}

		if (packet instanceof GameLevelPacket) {
			this.setLatestGameLevel((GameLevelPacket) packet);
		}
	}

	public void tick() {
	}

	public ArrayList<AvailableGameInfo> getCachedAvailableGames() {
		return this.availableGames;
	}

	protected Promise<AvailableGamesPacket> fetchAvailableGames() {
		if (this.availableGamePromise != null) {
			return this.availableGamePromise.getPromise();
		}

		RequestAvailableGamesPacket request = new RequestAvailableGamesPacket();
		this.availableGamePromise = new PacketResponsePromise<>(this.session, request, AvailableGamesPacket.class);
		try {
			this.availableGamePromise.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.session.sendDataPacket(request);

		this.availableGamePromise.getPromise().onComplete(() -> {
			this.availableGamePromise.close();
			this.availableGamePromise = null;
		});

		return this.availableGamePromise.getPromise();
	}

	public Promise<Collection<AvailableGameInfo>> getAvailableGames() {
		Promise<Collection<AvailableGameInfo>> p = new Promise<>();

		if (this.session.getTick() - this.lastFetchAvailableGames > 80) {
			Promise<AvailableGamesPacket> packetPromise = this.fetchAvailableGames();
			packetPromise.onSuccess(() -> {
				p.resolve(packetPromise.getResult().games);
			});

			packetPromise.onFailure(p::reject);

			this.lastFetchAvailableGames = this.session.getTick();
		} else {
			p.resolve(this.getCachedAvailableGames());
		}

		return p;
	}

	public void requestJoinGame(int id) {
		PlayerActionPacket packet = new PlayerActionPacket();
		packet.gameId = id;
		packet.action = PlayerAction.JOIN_GAME;

		this.session.sendDataPacket(packet);
	}

	public void requestLeaveGame() {
		PlayerActionPacket packet = new PlayerActionPacket();
		packet.action = PlayerAction.LEAVE_GAME;

		this.session.sendDataPacket(packet);

		this.session.getPlayer().onLeftGame(); // todo: packet response
	}


	public void cipherHandshake(SecretKey secretKey, NetworkCipher beforeEncryption) {

		byte[] key = secretKey.getEncoded();
		String algorithm = secretKey.getAlgorithm();

		try {
			key = Base64.getEncoder().encode(beforeEncryption.encrypt(key));
			algorithm = Base64.getEncoder().encodeToString(beforeEncryption.encrypt(algorithm.getBytes()));
		} catch (CryptoException e) {
			throw new RuntimeException(e);
		}

		ClientToServerHandshakePacket handshake = new ClientToServerHandshakePacket();
		handshake.spec = new SecretKeySpec(key, algorithm);

		this.session.sendDataPacket(handshake, true);
	}

}
