package client.network.handler;

import client.Client;
import client.ClientSession;

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
}
