package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.uno.NetworkCard;

import java.io.IOException;
import java.util.ArrayList;

public class CardTransactionPacket extends DataPacket implements Clientbound {


	public static final int TYPE_ADD = 0;
	public static final int TYPE_REMOVE = 1;

	public static final int REASON_NONE = 0;
	public static final int REASON_DRAW_SELF = 1; // カードを自分で引いた
	public static final int REASON_DRAW = 2; // カードをプレイヤーに引かされた
	public static final int REASON_PLAY = 3; // カードをプレイした
	public static final int REASON_PLAY_AFTER_DRAW = 4; // カードを自分で引いた後にプレイした
	public static final int REASON_FORGET_SHOUT = 5; // UNOの言い忘れ


	public int reason = 0;

	public int type;
	public ArrayList<NetworkCard> cards = new ArrayList<>();
	// todo: card array

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.type = in.readInt();
		this.reason = in.readInt();
		this.cards = in.produceArrayList((current) -> NetworkCard.read(in));
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.type);
		out.writeInt(this.reason);
		out.consumeArrayList(this.cards, (card) -> card.write(out));
	}

	@Override
	public String getName() {
		return "CardTransactionPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.CARD_TRANSACTION_PACKET;
	}
}
