package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.CardTransaction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CardTransactionPacket extends DataPacket implements Clientbound {

	public CardTransaction transaction = new CardTransaction();


	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.transaction = CardTransaction.readFrom(in);
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		this.transaction.write(out);
	}

	@Override
	public @NotNull String getName() {
		return "CardTransactionPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.CARD_TRANSACTION_PACKET;
	}
}
