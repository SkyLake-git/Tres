package com.tres.network.packet.protocol.types;

public enum PlayerAction {

	JOIN_GAME(1);

	public final int id;

	PlayerAction(int action) {
		this.id = action;
	}

	public static PlayerAction actionOf(int id) {
		for (PlayerAction e : values()) {
			if (e.id == id) {
				return e;
			}
		}

		return null;
	}
}
