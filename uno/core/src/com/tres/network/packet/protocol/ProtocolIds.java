package com.tres.network.packet.protocol;

public enum ProtocolIds {
	UNKNOWN(-1),
	TEXT_PACKET(1),
	DISCONNECT_PACKET(2),
	ALIVE_SIGNAL_PACKET(3),
	LOGIN_STATUS_PACKET(4),
	LOGIN_PACKET(5),
	CLIENT_INFO_PACKET(6),
	PLAYER_INITIALIZED_PACKET(7),
	PLAYER_INFO_REQUEST_PACKET(8),
	GAME_EVENT_PACKET(9),
	GAME_LEVEL_PACKET(10),
	ADD_PLAYER_PACKET(11),
	CARD_ACTION_PACKET(12),
	REQUEST_AVAILABLE_GAMES_PACKET(13),
	AVAILABLE_GAMES_PACKET(14),
	PLAYER_ACTION_PACKET(15),
	PLAYER_ACTION_RESPONSE_PACKET(16),
	SERVER_TO_CLIENT_HANDSHAKE_PACKET(17),
	CLIENT_TO_SERVER_HANDSHAKE_PACKET(18);

	public static final int PROTOCOL = 4;
	public static final int VERSION = 1;

	public final int id;

	ProtocolIds(int id) {
		this.id = id;
	}
}
