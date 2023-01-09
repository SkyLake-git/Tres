package com.tres.network.packet;

import java.io.IOException;
import java.util.ArrayList;

public class PacketBatch {

	protected String buffer;

	PacketBatch(String buffer) {
		this.buffer = buffer;
	}

	static PacketBatch fromPackets(ArrayList<Packet> packets) {
		try {
			PacketEncoder encoder = new PacketEncoder();
			for (Packet packet : packets) {
				PacketEncoder sub = new PacketEncoder();
				packet.encode(sub);
				try {
					encoder.writeString(sub.getStream().toString());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			return new PacketBatch(encoder.getStream().toString());
		} catch (Exception e) {

		}

		return null;
	}

	public String getBuffer() {
		return buffer;
	}
}
