package com.tres.network.packet;

import com.tres.utils.AbsorbConsumer;
import com.tres.utils.AbsorbRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

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

	public void writeNBytes(String s) throws IOException {
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

	public void writeShort(short v) throws IOException {
		this.stream.writeShort(v);
	}

	public <T> void writeNullable(T v, AbsorbRunnable writer) throws IOException {
		this.writeIf(v, v != null, writer);
	}

	public void writeNaturalNumber(int v) throws IOException {
		this.writeIf(v, v >= 0, () -> this.writeInt(v)); // 自然数だが 0を含む (含む、含まないどちらの意見もある)
	}

	public void writeNaturalNumber(short v) throws IOException {
		this.writeIf(v, v >= 0, () -> this.writeShort(v));
	}

	public <T> void writeIf(T v, boolean ifv, AbsorbRunnable writer) throws IOException {
		this.writeBoolean(ifv);

		if (ifv) {
			try {
				writer.run();
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public <T> void consumeArrayList(ArrayList<T> list, AbsorbConsumer<T> consumer) throws IOException {
		this.writeInt(list.size());
		for (T item : list) {
			try {
				consumer.accept(item);
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
