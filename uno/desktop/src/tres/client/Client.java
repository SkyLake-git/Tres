package tres.client;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tres.event.EventEmitter;
import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.compression.CompressException;
import com.tres.network.packet.compression.ZlibCompressor;
import com.tres.network.packet.protocol.PacketPool;
import com.tres.network.packet.protocol.types.JwtUtils;
import com.tres.snooze.Sleeper;
import com.tres.utils.Colors;
import com.tres.utils.Heartbeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;

public class Client implements Heartbeat.Syncable {

	protected Socket socket;

	protected Heartbeat heartbeat;

	protected EventEmitter eventEmitter;

	protected Logger logger;

	protected PacketPool packetPool;

	protected ServerListener listener;

	protected int tick;

	protected boolean isClosed;
	protected boolean isRunning;

	protected ClientSession session;

	protected Sleeper tickSleeper;

	protected String loginJWT;

	public Client() {
		this.socket = null;

		this.heartbeat = new Heartbeat(20);
		this.heartbeat.getList().add(this);

		this.logger = LoggerFactory.getLogger(this.getClass());

		this.packetPool = new PacketPool();

		this.listener = null;

		this.tick = 0;

		this.isClosed = false;
		this.isRunning = false;

		this.session = null;

		this.eventEmitter = new EventEmitter();

		this.tickSleeper = new Sleeper();

		this.prepareLoginJWT();
	}

	public String getLoginJWT() {
		return loginJWT;
	}

	public void prepareLoginJWT(){
		this.loginJWT = JWT.create()
				.withIssuer(JwtUtils.issuer)
				.withAudience("server")
				.withSubject("login")
				.sign(Algorithm.none());
	}

	public Sleeper getTickSleeper() {
		return tickSleeper;
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

	public boolean isRunning() {
		return isRunning;
	}

	public ClientSession getSession() {
		return session;
	}

	public Socket getSocket() {
		return socket;
	}

	public Logger getLogger() {
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

	public EventEmitter getEventEmitter() {
		return eventEmitter;
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


		byte[] result = data;
		//todo: split

		if (this.getSession().getCipher() != null){
			try {
				result = this.getSession().getCipher().decrypt(result);
			} catch (CryptoException e) {
				this.logger.warn("Packet decryption failed: " + Base64.getEncoder().encodeToString(result));
				e.printStackTrace();
				return;
			}
		}

		try {
			result = ZlibCompressor.getInstance().decompress(result);
		} catch (CompressException e) {
			this.logger.warn("Packet decompression failed: " + Arrays.toString(result));
			e.printStackTrace();
			return;
		}

		DataPacket finalPacket = this.packetPool.getPacketFull(result);
		if (finalPacket != null) {
			this.handlePacket(finalPacket);
		} else {
			this.logger.info("Ignored unknown data: " + Arrays.toString(result));
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

		this.tickSleeper.processNotifications();

		this.session.tick();
	}

}
