package com.tres.client.network.handler;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.protocol.*;

public interface PacketHandler {

	void handleText(TextPacket packet);

	void handleDisconnect(DisconnectPacket packet);

	void handleAliveSignal(AliveSignalPacket packet);

	void handleLoginStatus(LoginStatusPacket packet);

	void handlePlayerInitialized(PlayerInitializedPacket packet);

	void handleGameEvent(GameEventPacket packet);

	void handle(Clientbound packet);
}
