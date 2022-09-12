package client.network.handler;

import client.Client;
import network.packet.protocol.AliveSignalPacket;
import network.packet.protocol.DisconnectPacket;
import network.packet.protocol.TextPacket;

public class InGamePacketHandler extends BasePacketHandler {

	public InGamePacketHandler(Client client) {
		super(client);
	}

	@Override
	public void handleText(TextPacket packet) {
		this.client.getLogger().info(String.format("<%s> %s", packet.sourceName, packet.message));
	}

	@Override
	public void handleDisconnect(DisconnectPacket packet) {

	}

	@Override
	public void handleAliveSignal(AliveSignalPacket packet) {

	}
}
