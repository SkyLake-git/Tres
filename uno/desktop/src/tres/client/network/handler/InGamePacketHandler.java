package tres.client.network.handler;

import tres.client.Client;
import tres.client.ClientSession;
import tres.client.event.LoginCompleteEvent;
import com.tres.network.packet.ClientInfo;
import com.tres.network.packet.PlayerInfo;
import com.tres.network.packet.cipher.CommonKeyNetworkCipher;
import com.tres.network.packet.protocol.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

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

			KeyGenerator generator;

			try {
				generator = KeyGenerator.getInstance("AES");
				generator.init(256);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}

			SecretKey key = generator.generateKey();
			CommonKeyNetworkCipher cipher = new CommonKeyNetworkCipher(key);

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
}
