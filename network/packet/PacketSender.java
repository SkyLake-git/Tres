package network.packet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PacketSender {
	protected ArrayList<Packet> flush;
	protected Socket socket;

	protected boolean isClosed;

	public PacketSender(Socket socket) {
		this.socket = socket;
		this.flush = new ArrayList<Packet>();
		this.isClosed = false;
	}

	public void close(boolean flush) {
		if (flush) {
			this.sendFlush();
		}

		this.isClosed = true;
	}

	public void close() {
		this.close(true);
	}

	public void tick() {
		this.sendFlush();
	}

	public void sendFlush() {
		this.flush.forEach(packet -> {
			this.flushPacket(packet);
		});

		this.flush.clear();
	}

	public void sendPacket(Packet packet) {
		this.flush.add(packet);
	}

	protected void flushPacket(Packet packet) {
		if (this.socket.isClosed() || this.isClosed) {
			return;
		}

		try {
			OutputStream output = this.socket.getOutputStream();
			PacketEncoder out = new PacketEncoder();
			packet.encode(out);

			byte[] buffer = out.getByteArrayOutputStream().toByteArray();
			output.write(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
