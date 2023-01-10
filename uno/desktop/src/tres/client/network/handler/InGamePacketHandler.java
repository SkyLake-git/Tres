package tres.client.network.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tres.network.packet.cipher.PublicKeyNetworkCipher;
import com.tres.network.packet.protocol.types.JwtUtils;
import com.tres.network.packet.protocol.types.PacketHandlingException;
import com.tres.utils.Colors;
import sun.nio.cs.UTF_8;
import tres.client.Client;
import tres.client.ClientSession;
import tres.client.event.LoginCompleteEvent;
import com.tres.network.packet.ClientInfo;
import com.tres.network.packet.PlayerInfo;
import com.tres.network.packet.cipher.CommonKeyNetworkCipher;
import com.tres.network.packet.protocol.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
			response.remoteAddress = this.session.getClient().getSocket().getLocalAddress().toString();
			response.info = new ClientInfo("RequestedName");

			this.session.sendDataPacket(response);
		}
	}

	@Override
	public void handlePlayerInitialized(PlayerInitializedPacket packet) {
		this.session.getLogger().info("Player initialized as server! name: " + packet.name + " runtimeId: " + packet.runtimeId);

		this.session.createPlayer(new PlayerInfo(packet.name));

		this.client.getEventEmitter().emit(new LoginCompleteEvent());
	}

	@Override
	public void handleGameEvent(GameEventPacket packet) {

	}

	@Override
	public void handleServerToClientHandshake(ServerToClientHandshakePacket packet) {
		if (this.session.getPacketSender().getCipher() != null){
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

	protected PublicKey processServerToClientHandshakeJWT(String token){
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
		} catch (JWTVerificationException e){
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
