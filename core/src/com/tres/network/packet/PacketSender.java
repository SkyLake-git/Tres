package com.tres.network.packet;


import com.tres.network.packet.compression.CompressException;
import com.tres.network.packet.compression.ZlibCompressor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PacketSender {
	protected ArrayList<Packet> flush;
	protected Socket socket;

	protected boolean isClosed;

	protected NetworkSettings settings;

	public PacketSender(Socket socket, NetworkSettings settings) {
		this.socket = socket;
		this.flush = new ArrayList<>();
		this.isClosed = false;
		this.settings = settings;
	}

	public void close(boolean flush) {
		if (flush) {
			try {
				this.sendFlush();
			} catch (CompressException e) {
			}
		}

		this.isClosed = true;
	}

	public void close() {
		this.close(true);
	}

	public void tick() throws CompressException {
		this.sendFlush();
	}

	public void sendFlush() throws CompressException {
		for (Packet packet : this.flush) {
			flushPacket(packet);
		}

		this.flush.clear();
	}

	public void sendPacket(Packet packet) {
		this.flush.add(packet);
	}

	protected void flushPacket(Packet packet) throws CompressException {
		if (this.socket.isClosed() || this.isClosed) {
			return;
		}


		try {
			OutputStream output = this.socket.getOutputStream();
			PacketEncoder out = new PacketEncoder();
			packet.encode(out);

			byte[] buffer = out.getByteArrayOutputStream().toByteArray();

			if (this.settings.compression()) {
				byte[] compressed = ZlibCompressor.getInstance().compress(buffer);
				output.write(compressed);
			} else {
				output.write(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
