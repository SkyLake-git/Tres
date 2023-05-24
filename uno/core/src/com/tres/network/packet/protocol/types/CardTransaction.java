package com.tres.network.packet.protocol.types;

import com.tres.network.packet.InvalidPayloadException;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.container.ContainerIds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CardTransaction {

	public static final int TYPE_ADD = 0;

	public static final int TYPE_REMOVE = 1;

	// TYPE_CLEAR は CardListPacket に移動されました。
	// (CardTransactionPacketという名前とあっていないため)

	public static final int REASON_NONE = 0;

	public static final int REASON_DRAW_SELF = 1; // カードを自分で引いた

	public static final int REASON_DRAW = 2; // カードをプレイヤーに引かされた

	public static final int REASON_PLAY = 3; // カードをプレイした

	public static final int REASON_PLAY_AFTER_DRAW = 4; // カードを自分で引いた後にプレイした

	public static final int REASON_FORGET_SHOUT = 5; // UNOの言い忘れ

	// todo: network card 以外のtransactionにも対応
	public int container = ContainerIds.PLAYER;

	public short playerRuntimeId = -1;

	public int reason = REASON_NONE;

	public int type;

	public Collection<NetworkCard> cards = new ArrayList<>();

	public static CardTransaction readFrom(PacketDecoder in) throws IOException, InvalidPayloadException {
		CardTransaction tr = new CardTransaction();
		tr.read(in);

		return tr;
	}

	public void write(PacketEncoder out) throws IOException, InvalidPayloadException {
		out.writeInt(this.type);
		out.writeInt(this.container);
		out.writeIf(this.container == ContainerIds.PLAYER, () -> out.writeShort(this.playerRuntimeId));
		out.writeInt(this.reason);
		out.consumeArrayList(this.cards, (card) -> card.write(out));
	}

	public void read(PacketDecoder in) throws IOException, InvalidPayloadException {
		this.type = in.readInt();
		this.container = in.readInt();
		this.playerRuntimeId = in.readIf(in::readShort, (short) -1);
		if (this.container == ContainerIds.PLAYER && this.playerRuntimeId < 0) {
			throw new InvalidPayloadException("Player runtime id not found"); // fixme: クライアント側でエラーが起きる
		}
		this.reason = in.readInt();
		this.cards = in.produceArrayList((current) -> NetworkCard.read(in));
	}
}
