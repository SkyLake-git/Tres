package client;

import network.packet.Clientbound;
import network.packet.DataPacket;
import network.packet.protocol.PacketPool;
import utils.Colors;
import utils.Heartbeat;
import utils.MainLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Heartbeat.Syncable {

	protected Socket socket;

	protected Heartbeat heartbeat;


	protected MainLogger logger;

	protected PacketPool packetPool;

	protected ServerListener listener;

	protected int tick;

	protected boolean isClosed;
	protected boolean isRunning;

	protected ClientSession session;

	Client() {
		try {
			this.socket = null;

			this.heartbeat = new Heartbeat(20);
			this.heartbeat.getList().add(this);

			this.logger = new MainLogger("Client");

			this.packetPool = new PacketPool();

			this.listener = null;

			this.tick = 0;

			this.isClosed = false;
			this.isRunning = false;

			this.session = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void start(InetSocketAddress address) throws IOException {
		if (!this.heartbeat.isAlive()) {
			this.socket = new Socket();
			this.socket.connect(address);

			try {
				this.listener = new ServerListener(this, this.socket);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			this.session = new ClientSession(this);


			this.isRunning = true;

			this.heartbeat.start();
			this.listener.start();
		}
	}

	public Socket getSocket() {
		return socket;
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

	public boolean isClosed() {
		return isClosed;
	}

	private void closeCleanup() {

	}

	public void close() {
		if (this.isClosed || !this.isRunning) {
			return;
		}

		this.logger.info(Colors.wrap("Closing client...", Colors.YELLOW_BOLD_BRIGHT));
		this.listener.interrupt();
		this.logger.info("Interrupted ServerListener");
		this.heartbeat.interrupt();
		this.logger.info("Interrupted heartbeat");

		this.session.close();
		this.isRunning = false;

		this.logger.info(Colors.wrap("Successfully closed client", Colors.BLUE_BRIGHT));
		this.isClosed = true;

		this.closeCleanup();
	}

	void onReceiveRaw(byte[] data) {
		// this.logger.info("Received: " + new String(data, StandardCharsets.UTF_8));

		DataPacket packet = this.packetPool.getPacketFull(data);
		if (packet != null) {
			this.handlePacket(packet);
		}
	}

	protected void handlePacket(DataPacket packet) {
		if (!(packet instanceof Clientbound)) {
			this.logger.info("Invalid packet received: " + packet.getName());
		}

		this.session.handlePacket(packet);
	}

	public void tick() {
		this.tick++;

		this.session.tick();
	}

}
