package com.tres.network.packet;

import java.io.IOException;

public class PlayerInfo {

	protected String username;

	public PlayerInfo(String username) {
		this.username = username;
	}

	public static PlayerInfo read(PacketDecoder in) throws IOException {
		return new PlayerInfo(in.readUTFString());
	}

	public String getUsername() {
		return username;
	}

	public void write(PacketEncoder out) throws IOException {
		out.writeUTFString(this.username);
	}

}
