package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

public class GameEventPacket extends DataPacket implements Clientbound {

	public static final int IN_GAME = 0x01;

	public static final int TURN_START = 0x01 | IN_GAME;
	public static final int TURN_END = 0x02 | IN_GAME;

	public int event;

	public int playerRuntimeId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.event = in.readInt();
		boolean hasPlayer = in.getStream().readBoolean();
		if (hasPlayer) {
			this.playerRuntimeId = in.getStream().readShort();
		}
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.event);
		out.getStream().writeShort(this.playerRuntimeId);
		out.getStream().writeBoolean(this.playerRuntimeId >= 0);
		if (this.playerRuntimeId >= 0) {
			out.getStream().writeShort(this.playerRuntimeId);
		}
	}

	@Override
	public String getName() {
		return "GameEventPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.GAME_EVENT_PACKET;
	}
}
