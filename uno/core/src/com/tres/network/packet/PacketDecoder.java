package com.tres.network.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PacketDecoder {

	protected DataInputStream stream;

	public PacketDecoder(byte[] buffer) {
		this.stream = new DataInputStream(new ByteArrayInputStream(buffer));
	}

	public DataInputStream getStream() {
		return stream;
	}

	public String readUTFString() throws IOException {
		return this.stream.readUTF();
	}

	public int readInt() throws IOException {
		return this.stream.readInt();
	}

	public long readLong() throws IOException {
		return this.stream.readLong();
	}

	public double readDouble() throws IOException {
		return this.stream.readDouble();
	}

	public int readUnsignedByte() throws IOException {
		return this.stream.readUnsignedByte();
	}

	public boolean readBoolean() throws IOException {
		return this.stream.readBoolean();
	}

	public byte[] readNBytes() throws IOException{
		int n = this.stream.readInt();
		byte[] bytes = new byte[n];
		for (int i = 0; i < n; i++) {
			bytes[i] = this.getStream().readByte();
		}

		return bytes;
	}
}
