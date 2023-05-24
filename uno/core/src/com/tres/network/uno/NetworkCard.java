package com.tres.network.uno;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.utils.Utils;

import java.io.IOException;

public class NetworkCard {

	private static int nextId = 0;

	public final int runtimeId;

	protected Card card;

	public NetworkCard(int runtimeId, Card card) {
		this.runtimeId = runtimeId;
		this.card = card;
	}

	public NetworkCard(int runtimeId) {
		this.runtimeId = runtimeId;
		this.card = null;
	}

	public static int nextRuntimeId() {
		return nextId++;
	}

	public static NetworkCard read(PacketDecoder in) throws IOException {
		NetworkCard s = new NetworkCard(in.readInt());
		s.readCardInfo(in);

		return s;
	}

	public boolean hasCardInfo() {
		return this.card != null;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getRuntimeId() {
		return runtimeId;
	}

	public void readCardInfo(PacketDecoder in) throws IOException {
		if (in.readBoolean()) {
			int symbolOrd = in.readInt();
			int colorOrd = in.readInt();
			this.card = new Card(Utils.getEnumOrdinal(Card.Symbol.class, symbolOrd), Utils.getEnumOrdinal(Card.Color.class, colorOrd));
		} else {
			this.card = null;
		}
	}

	public void writeCardInfo(PacketEncoder out) throws IOException {
		out.writeIf(this.card != null, () -> {
			out.writeInt(this.card.symbol.ordinal());
			out.writeInt(this.card.color.ordinal());
		});
	}

	public void write(PacketEncoder out) throws IOException {
		out.writeInt(this.runtimeId);
		this.writeCardInfo(out);
	}

	public NetworkCard withNoInfo() {
		return new NetworkCard(this.runtimeId);
	}
}
