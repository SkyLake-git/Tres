package client.network.handler;

import network.packet.protocol.*;

public interface PacketHandler {

	void handleText(TextPacket packet);

	void handleDisconnect(DisconnectPacket packet);

	void handleAliveSignal(AliveSignalPacket packet);

	void handleLoginStatus(LoginStatusPacket packet);

	void handlePlayerInitialized(PlayerInitializedPacket packet);
}
