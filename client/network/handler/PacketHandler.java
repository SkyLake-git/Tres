package client.network.handler;

import network.packet.protocol.AliveSignalPacket;
import network.packet.protocol.DisconnectPacket;
import network.packet.protocol.TextPacket;

public interface PacketHandler {

	void handleText(TextPacket packet);

	void handleDisconnect(DisconnectPacket packet);

	void handleAliveSignal(AliveSignalPacket packet);
}
