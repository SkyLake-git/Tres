package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

public class CardListPacket extends DataPacket implements Clientbound {

	public final static int OP_CLEAR = 0;
	public final static int OP_ENABLE = 1;

	public int operation;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.operation = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.operation);
	}

	@Override
	public String getName() {
		return "CardListPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.CARD_LIST_PACKET;
	}
}
