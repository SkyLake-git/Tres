package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
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
			try {
				byte[] buffer = new byte[1024];
				int size = this.stream.read(buffer);

				buffer = Arrays.copyOf(buffer, size);
				// System.out.println(new String(this.buf, StandardCharsets.UTF_8));

				this.client.onReceiveRaw(buffer);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
