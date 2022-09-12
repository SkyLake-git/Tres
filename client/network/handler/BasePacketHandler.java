package client.network.handler;

import client.Client;

abstract public class BasePacketHandler implements PacketHandler {

	protected Client client;

	public BasePacketHandler(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}
}
