package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.uno.container.ContainerIds;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CardListPacket extends DataPacket implements Clientbound {

	public final static int OP_CLEAR = 0;

	public final static int OP_ENABLE = 1;

	public int operation;

	public int container = ContainerIds.PLAYER;

	public short playerRuntimeId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.operation = in.readInt();
		this.container = in.readInt();
		this.playerRuntimeId = in.readIf(in::readShort, (short) -1);

		if (this.container == ContainerIds.PLAYER && this.playerRuntimeId < 0) {
			throw new InvalidPayloadException("Player runtime id not found"); // fixme: クライアント側でエラーが起きる
		}
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.operation);
		out.writeInt(this.container);
		out.writeIf(this.container == ContainerIds.PLAYER, () -> out.writeShort(this.playerRuntimeId));
	}

	@Override
	public @NotNull String getName() {
		return "CardListPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.CARD_LIST_PACKET;
	}
}
