package com.tres.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketEncoder {

	protected DataOutputStream stream;
	public ByteArrayOutputStream internalStream;

	public PacketEncoder() {
		this.internalStream = new ByteArrayOutputStream();
		this.stream = new DataOutputStream(this.internalStream);
	}

	public PacketEncoder(OutputStream outputStream) {
		this.internalStream = null;
		this.stream = new DataOutputStream(outputStream);
	}

	public ByteArrayOutputStream getByteArrayOutputStream() {
		return internalStream;
	}

	public DataOutputStream getStream() {
		return stream;
	}

	public void writeUTFString(String data) throws IOException {
		this.stream.writeUTF(data);
	}

	public void writeInt(int v) throws IOException {
		this.stream.writeInt(v);
	}

	public void writeFloat(float v) throws IOException {
		this.stream.writeFloat(v);
	}

	public void writeNBytes(String s) throws IOException{
		this.stream.writeInt(s.length());
		this.stream.writeBytes(s);
	}

	public void writeDouble(double v) throws IOException {
		this.stream.writeDouble(v);
	}

	public void writeBoolean(boolean v) throws IOException {
		this.stream.writeBoolean(v);
	}

	public void writeLong(long v) throws IOException {
		this.stream.writeLong(v);
	}
}
