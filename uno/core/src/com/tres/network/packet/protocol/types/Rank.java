package com.tres.network.packet.protocol.types;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

import java.io.IOException;

public class Rank {

	public int place;

	public Rank(int place) {
		this.place = place;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public void write(PacketEncoder out) throws IOException {
		out.writeInt(this.place);
	}

	public static Rank read(PacketDecoder in) throws IOException {
		return new Rank(in.readInt());
	}
}
