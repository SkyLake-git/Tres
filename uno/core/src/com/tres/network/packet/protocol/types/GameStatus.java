package com.tres.network.packet.protocol.types;

public enum GameStatus {

	NOTHING(0),
	PLAYING(1),
	ENDING(2);

	public final int id;

	GameStatus(int id) {
		this.id = id;
	}

	public static GameStatus statusOf(int id) {
		for (GameStatus e : values()) {
			if (e.id == id) {
				return e;
			}
		}

		return null;
	}
}
