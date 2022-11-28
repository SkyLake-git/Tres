package com.tres.client;

import com.tres.utils.Colors;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class ServerListener extends Thread {

	protected Client client;

	protected InputStream stream;

	protected Socket server;

	public ServerListener(Client client, Socket server) throws IOException {
		this.client = client;
		this.server = server;
		this.stream = this.server.getInputStream();
	}

	public void run() {
		while (!this.isInterrupted()) {
			if (this.server.isClosed()) {
				return;
			}

			try {
				byte[] buffer = new byte[1024];
				int size = this.stream.read(buffer);
				if (size != -1) {
					buffer = Arrays.copyOf(buffer, size);
					// System.out.println(new String(this.buf, StandardCharsets.UTF_8));

					this.client.onReceiveRaw(buffer);
				} else {
					if (!this.client.isClosed()) {
						this.client.getLogger().warn("Socket closed by server (not DisconnectPacket)");
						this.client.close();
					}
				}
			} catch (SocketException e) {
				if (this.isInterrupted()) {
					return;
				}
				this.client.getLogger().warn(Colors.wrap("Connection reset (SocketException)", Colors.RED_BACKGROUND));
				this.client.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
