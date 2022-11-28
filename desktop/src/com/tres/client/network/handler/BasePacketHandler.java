package com.tres.client.network.handler;

import com.tres.client.Client;
import com.tres.client.ClientSession;
import com.tres.network.packet.Clientbound;
import com.tres.network.packet.protocol.*;

abstract public class BasePacketHandler implements PacketHandler {

	protected Client client;

	protected ClientSession session;

	public BasePacketHandler(Client client, ClientSession session) {
		this.client = client;
		this.session = session;
	}

	public ClientSession getSession() {
		return session;
	}

	public Client getClient() {
		return client;
	}

	public void handle(Clientbound packet) {
		if (packet instanceof TextPacket) {
			this.handleText((TextPacket) packet);
		} else if (packet instanceof DisconnectPacket) {
			this.handleDisconnect((DisconnectPacket) packet);
		} else if (packet instanceof AliveSignalPacket) {
			this.handleAliveSignal((AliveSignalPacket) packet);
		} else if (packet instanceof LoginStatusPacket) {
			this.handleLoginStatus((LoginStatusPacket) packet);
		} else if (packet instanceof PlayerInitializedPacket) {
			this.handlePlayerInitialized((PlayerInitializedPacket) packet);
		} else if (packet instanceof GameEventPacket) {
			this.handleGameEvent((GameEventPacket) packet);
		}
	}
}
