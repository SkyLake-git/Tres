package com.tres.network.uno;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.utils.Utils;

import java.io.IOException;

public class NetworkCard extends Card {

	private static int nextId = 0;

	public static int nextRuntimeId() {
		return nextId++;
	}

	public static NetworkCard by(int runtimeId, Card card) {
		return new NetworkCard(runtimeId, card.symbol, card.color);
	}


	public final int runtimeId;

	public NetworkCard(int runtimeId, Symbol symbol, Color color) {
		super(symbol, color);

		this.runtimeId = runtimeId;
	}

	public void write(PacketEncoder out) throws IOException {
		out.writeInt(this.runtimeId);
		out.writeInt(this.symbol.ordinal());
		out.writeInt(this.color.ordinal());
	}

	public static NetworkCard read(PacketDecoder in) throws IOException, IndexOutOfBoundsException {
		int runtimeId = in.readInt();
		int symbolOrdinal = in.readInt();
		int colorOrdinal = in.readInt();

		return new NetworkCard(runtimeId, Utils.getEnumOrdinal(Symbol.class, symbolOrdinal), Utils.getEnumOrdinal(Color.class, colorOrdinal));
	}
}
