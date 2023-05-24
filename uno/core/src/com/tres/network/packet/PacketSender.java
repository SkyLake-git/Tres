package com.tres.network.packet;


import com.tres.network.IOWrapper;
import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.cipher.NetworkCipher;
import com.tres.network.packet.compression.CompressException;
import com.tres.network.packet.compression.ZlibCompressor;

import java.io.IOException;
import java.util.ArrayList;

public class PacketSender {
	protected final ArrayList<Packet> flush;

	protected IOWrapper wrapper;

	protected boolean isClosed;

	protected NetworkSettings settings;

	protected NetworkCipher cipher;

	public PacketSender(IOWrapper wrapper, NetworkSettings settings) {
		this.wrapper = wrapper;
		this.flush = new ArrayList<>();
		this.isClosed = false;
		this.settings = settings;
	}

	public NetworkSettings getSettings() {
		return settings;
	}

	public void setSettings(NetworkSettings settings) {
		this.settings = settings;
	}

	public NetworkCipher getCipher() {
		return cipher;
	}

	public void setCipher(NetworkCipher cipher) {
		this.cipher = cipher;
	}

	public void close(boolean flush) {
		if (flush) {
			try {
				this.sendFlush();
			} catch (CompressException | CryptoException | PacketProcessingException ignored) {
			}
		}

		this.isClosed = true;
	}

	public void close() {
		this.close(true);
	}

	public void tick() throws CompressException, CryptoException, PacketProcessingException {
		this.sendFlush();
	}

	public void sendFlush() throws CompressException, CryptoException, PacketProcessingException {
		synchronized (this.flush) {
			if (this.flush.size() == 0) {
				return;
			}

			PacketBatch batch = new PacketBatch();
			for (Packet packet : this.flush) {
				try {
					byte[] payload = this.encode(packet);

					batch.write(payload);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			}

			this.output(batch);

			this.flush.clear();
		}
	}

	public void sendPacket(Packet packet) {
		synchronized (this.flush) {
			this.flush.add(packet);
		}
	}

	protected byte[] encode(Packet packet) throws IOException, PacketProcessingException {
		if (!this.wrapper.isOpen() || this.isClosed) {
			throw new IOException("IO wrapper or sender closed.");
		}


		PacketEncoder out = new PacketEncoder();
		packet.encode(out);


		return out.getByteArrayOutputStream().toByteArray();
	}

	protected void output(PacketBatch batch) throws CompressException, CryptoException {
		byte[] buffer = batch.getBuffer();

		if (this.settings.compression()) {
			buffer = ZlibCompressor.getInstance().compress(buffer);
		}

		if (this.settings.encryption() && this.cipher != null) {
			buffer = this.cipher.encrypt(buffer);
		}

		this.wrapper.write(buffer);
	}

}
