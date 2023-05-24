package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GameEventPacket extends DataPacket implements Clientbound {

	public static final int START = 0x02;

	public static final int CLOSE = 0x04;

	public static final int IN_GAME = 0x01;

	public static final int SYSTEM = 0x02;

	public static final int TURN_START = 0x01 | IN_GAME;

	public static final int TURN_END = 0x02 | IN_GAME;

	public static final int SOMEONE_OUT = 0x04 | IN_GAME; // 誰かがあがった (カードを使い切った)

	public static final int END = 0x08 | IN_GAME; // 一人を除いた全員が上がった (全員の順位が決まった) これは実質的なゲームの終了イベントです

	public static final int UNO_SHOUT = 0x16 | IN_GAME;

	public static final int UNO_ACCUSATION = 0x32 | IN_GAME;

	public static final int PLAYER_JOIN = 0x01 | SYSTEM;

	public static final int PLAYER_LEFT = 0x02 | SYSTEM;

	public int event;

	public int gameId;

	public short playerRuntimeId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.event = in.readInt();
		this.gameId = in.readInt();
		this.playerRuntimeId = in.readNaturalShort();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.event);
		out.writeInt(this.gameId);
		out.writeNaturalNumber(this.playerRuntimeId);
	}

	@Override
	public @NotNull String getName() {
		return "GameEventPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.GAME_EVENT_PACKET;
	}
}
