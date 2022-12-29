package com.tres.network.packet;

import java.io.IOException;

public class ClientInfo extends PlayerInfo {

	public ClientInfo(String username) {
		super(username);
	}

	public static ClientInfo read(PacketDecoder in) throws IOException {
		return new ClientInfo(in.readString());
	}
}
