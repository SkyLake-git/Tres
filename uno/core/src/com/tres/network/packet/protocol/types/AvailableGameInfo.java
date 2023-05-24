package com.tres.network.packet.protocol.types;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

import java.io.IOException;

public class AvailableGameInfo {

	protected int players;

	protected int gameId;

	public AvailableGameInfo(int players, int gameId) {
		this.players = players;
		this.gameId = gameId;
	}

	public static AvailableGameInfo read(PacketDecoder in) throws IOException {

		return new AvailableGameInfo(in.readInt(), in.readInt());
	}

	public int getPlayers() {
		return players;
	}

	public int getGameId() {
		return gameId;
	}

	public void write(PacketEncoder out) throws IOException {
		out.writeInt(this.players);
		out.writeInt(this.gameId);
	}
}
