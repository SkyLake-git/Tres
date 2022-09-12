package client;

import network.packet.Clientbound;
import network.packet.DataPacket;
import network.packet.PacketSender;
import network.packet.protocol.PacketPool;
import network.packet.protocol.TextPacket;
import utils.Colors;
import utils.MainLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {

	protected Socket socket;

	protected ClientHeartbeat heartbeat;

	protected PacketSender sender;

	protected MainLogger logger;

	protected PacketPool packetPool;

	protected ServerListener listener;

	protected int tick;

	Client() {
		try {
			this.socket = new Socket();
			this.socket.connect(new InetSocketAddress("127.0.0.1", 34560));

			this.heartbeat = new ClientHeartbeat(this, 20);

			this.sender = new PacketSender(this.socket);

			this.logger = new MainLogger("Client");

			this.packetPool = new PacketPool();

			this.listener = new ServerListener(this, this.socket);

			this.tick = 0;

			this.heartbeat.start();
			this.listener.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public PacketSender getSender() {
		return sender;
	}

	public MainLogger getLogger() {
		return logger;
	}

	public PacketPool getPacketPool() {
		return packetPool;
	}

	public int getTick() {
		return tick;
	}

	void close() {
		this.logger.info(Colors.wrap("Closing client...", Colors.YELLOW_BOLD_BRIGHT));
		this.sender.close(false);
		this.listener.interrupt();
		this.logger.info("Interrupted ServerListener");
		this.heartbeat.interrupt();
		this.logger.info("Interrupted ClientHeartbeat");
		try {
			this.socket.close();
			this.logger.info("Closing socket");
		} catch (IOException e) {
			this.logger.warning("Failed to close socket");
		}

		this.logger.info(Colors.wrap("Successfully closed client", Colors.BLUE_BRIGHT));


	}

	void onReceiveRaw(byte[] data) {
		this.logger.info("Received: " + new String(data, StandardCharsets.UTF_8));

		DataPacket packet = this.packetPool.getPacketFull(data);
		if (packet != null) {
			this.handlePacket(packet);
		}
	}

	protected void handlePacket(DataPacket packet) {
		if (!(packet instanceof Clientbound)) {
			this.logger.info("Invalid packet received: " + packet.getName());
		}

		this.logger.info("Packet Success: " + packet.getName());
	}

	void tick() {
		this.tick++;
		this.sender.tick();

		if (this.tick % 40 == 0) {
			TextPacket packet = new TextPacket();
			packet.message = "Hello";
			packet.sourceName = "Yeet";

			this.sender.sendPacket(packet);
		}

	}

}
