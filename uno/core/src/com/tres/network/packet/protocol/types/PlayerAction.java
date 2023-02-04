package com.tres.network.packet.protocol.types;

public enum PlayerAction {

	JOIN_GAME(1),
	LEAVE_GAME(2);

	public final int id;

	PlayerAction(int action) {
		this.id = action;
	}
}
