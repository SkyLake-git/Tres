package network.packet;

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

	public String readString() throws IOException {
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

}
