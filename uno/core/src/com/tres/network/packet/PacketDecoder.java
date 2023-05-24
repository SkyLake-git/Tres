package com.tres.network.packet;

import com.tres.utils.AbsorbFunction;
import com.tres.utils.AbsorbRunnable;
import com.tres.utils.AbsorbSupplier;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PacketDecoder {

	protected DataInputStream stream;

	protected ByteArrayInputStream internalStream;

	public PacketDecoder(byte[] buffer) {
		this.internalStream = new ByteArrayInputStream(buffer);
		this.stream = new DataInputStream(this.internalStream);
	}

	public ByteArrayInputStream getByteArrayInputStream() {
		return internalStream;
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

	public short readShort() throws IOException {
		return this.stream.readShort();
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

	public byte[] readNBytes() throws IOException {
		int n = this.stream.readInt();
		byte[] bytes = new byte[n];
		for (int i = 0; i < n; i++) {
			bytes[i] = this.getStream().readByte();
		}

		return bytes;
	}

	public byte[] readN() throws IOException {
		int n = this.stream.readInt();
		byte[] bytes = new byte[n];
		int size = this.stream.read(bytes);


		return Arrays.copyOf(bytes, size);
	}

	public <T> T readIf(AbsorbSupplier<T> supplier, T defaultValue) throws IOException {
		if (this.readBoolean()) {
			try {
				return supplier.get();
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return defaultValue;
	}

	public void runIf(AbsorbRunnable runnable) throws IOException {
		if (this.readBoolean()) {
			try {
				runnable.run();
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public short readNaturalShort() throws IOException {
		return this.readIf(this::readShort, (short) -1);
	}

	public int readNaturalInteger() throws IOException {
		return this.readIf(this::readInt, -1);
	}

	public <T> ArrayList<T> produceArrayList(AbsorbFunction<Integer, T> producer) throws IOException {
		int size = this.readInt();

		ArrayList<T> list = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			try {
				list.add(producer.apply(i));
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return list;
	}
}
