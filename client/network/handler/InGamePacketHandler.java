package client.network.handler;

import client.Client;
import client.ClientSession;
import network.packet.protocol.*;

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
		this.client.getLogger().warning("Disconnected by server. Reason: " + packet.reason);
		this.client.close();
	}

	@Override
	public void handleAliveSignal(AliveSignalPacket packet) {
		AliveSignalPacket response = new AliveSignalPacket();
		response.isig = packet.isig + 1;

		this.session.getPacketSender().sendPacket(response);
	}

	@Override
	public void handleLoginStatus(LoginStatusPacket packet) {
		if (packet.status == LoginStatusPacket.REQUEST_PROTOCOL) {
			ProtocolPacket response = new ProtocolPacket();
			response.protocol = ProtocolIds.PROTOCOL;
			response.version = ProtocolIds.VERSION;

			this.session.getPacketSender().sendPacket(response);
		} else if (packet.status == LoginStatusPacket.REQUEST_CLIENT_INFO) {
			PlayerInfoRequestPacket request = new PlayerInfoRequestPacket();
			request.name = "Requested Name";

			this.session.getPacketSender().sendPacket(request);
			
			ClientInfoPacket response = new ClientInfoPacket();
			response.remoteAddress = this.session.getClient().getSocket().getLocalAddress().toString();

			this.session.getPacketSender().sendPacket(response);
		}
	}

	@Override
	public void handlePlayerInitialized(PlayerInitializedPacket packet) {
		this.session.getLogger().info("Player initialized as server! name: " + packet.name + " runtimeId: " + packet.runtimeId);
	}
}
