package com.tres.network.packet;


import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.cipher.NetworkCipher;
import com.tres.network.packet.compression.CompressException;
import com.tres.network.packet.compression.ZlibCompressor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class PacketSender {
	protected final ArrayList<Packet> flush;
	protected Socket socket;

	protected boolean isClosed;

	protected NetworkSettings settings;

	protected NetworkCipher cipher;

	public PacketSender(Socket socket, NetworkSettings settings) {
		this.socket = socket;
		this.flush = new ArrayList<>();
		this.isClosed = false;
		this.settings = settings;
		this.cipher = cipher;
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
			} catch (CompressException | CryptoException e) {
			}
		}

		this.isClosed = true;
	}

	public void close() {
		this.close(true);
	}

	public void tick() throws CompressException, CryptoException {
		this.sendFlush();
	}

	public void sendFlush() throws CompressException, CryptoException {
		synchronized (this.flush){
			for (Packet packet : this.flush) {
				flushPacket(packet);
			}

			this.flush.clear();
		}
	}

	public void sendPacket(Packet packet) {
		synchronized (this.flush){
			this.flush.add(packet);
		}
	}

	protected void flushPacket(Packet packet) throws CompressException, CryptoException {
		if (this.socket.isClosed() || this.isClosed) {
			return;
		}


		try {
			OutputStream output = this.socket.getOutputStream();
			PacketEncoder out = new PacketEncoder();
			packet.encode(out);

			byte[] buffer = out.getByteArrayOutputStream().toByteArray();

			if (this.settings.compression()) {
				buffer = ZlibCompressor.getInstance().compress(buffer);
			}

			if (this.settings.encryption() && this.cipher != null) {
				buffer = this.cipher.encrypt(buffer);
			}

			output.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
