package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

public class CardActionPacket extends DataPacket implements Clientbound, Serverbound {

	private static final int ACTION_DRAW = 0;

	public int action;
	public int cardRuntimeId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.action = in.readInt();
		boolean hasCard = in.readBoolean();
		if (hasCard) {
			this.cardRuntimeId = in.readInt();
		}
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.action);
		out.writeBoolean(this.cardRuntimeId >= 1);
		if (this.cardRuntimeId >= 1) {
			out.writeInt(this.cardRuntimeId);
		}
	}

	@Override
	public String getName() {
		return "CardActionPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.CARD_ACTION_PACKET;
	}
}
