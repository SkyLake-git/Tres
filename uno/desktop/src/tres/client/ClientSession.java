package tres.client;


import com.badlogic.gdx.utils.Null;
import com.tres.network.SocketIOWrapper;
import com.tres.network.packet.*;
import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.cipher.NetworkCipher;
import com.tres.network.packet.compression.CompressException;
import com.tres.network.packet.protocol.DisconnectPacket;
import com.tres.network.packet.protocol.types.PacketHandlingException;
import com.tres.network.uno.Player;
import com.tres.utils.Colors;
import com.tres.utils.ParameterMethodCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tres.client.event.packet.DataPacketReceiveEvent;
import tres.client.event.packet.DataPacketSendEvent;
import tres.client.network.handler.InGamePacketHandler;
import tres.client.network.handler.PacketHandler;
import tres.client.uno.ClientPlayer;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

public class ClientSession {

	protected Client client;

	protected int tick;

	protected Logger logger;

	protected PacketHandler packetHandler;

	protected SecretKey cipherKey;

	protected PacketSender packetSender;

	protected ClientPlayer player;

	protected ParameterMethodCaller<Packet> packetHandlerCaller;

	protected ClientNetworkActions actions;

	@Null
	protected NetworkCipher cipher;

	protected SocketIOWrapper ioWrapper;

	ClientSession(Client client) {
		this.client = client;
		this.tick = 0;
		this.cipher = null;
		this.ioWrapper = new SocketIOWrapper(client.getSocket());
		this.packetHandlerCaller = null;
		this.packetSender = new PacketSender(this.ioWrapper, new NetworkSettings(true, true));
		this.setPacketHandler(new InGamePacketHandler(this.client, this));
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.player = null;
		this.cipherKey = null;

		this.actions = new ClientNetworkActions(this);
	}

	public ClientNetworkActions getActions() {
		return actions;
	}

	public SecretKey generateCipherKey() {
		KeyGenerator generator;

		try {
			generator = KeyGenerator.getInstance("AES");
			generator.init(256);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		return generator.generateKey();
	}

	public SecretKey getCipherKey() {
		return cipherKey;
	}

	public void setCipherKey(SecretKey cipherKey) {
		this.cipherKey = cipherKey;
	}

	void tick() {
		this.tick++;

		this.actions.tick();

		if (!this.isConnected()) {
			throw new RuntimeException("socket not connected");
		}

		try {
			this.packetSender.tick();
		} catch (CompressException e) {
			this.logger.info("Packet compression failed: tick flush");
			this.client.close();
		} catch (CryptoException e) {
			this.logger.info("Packet encryption failed: tick flush");
			this.client.close();
		} catch (PacketProcessingException e) {
			this.logger.info("Packet processing failed: tick flush");
			this.client.close();
		}
	}

	public void createPlayer(PlayerInfo info) {
		if (this.player == null) {
			this.player = new ClientPlayer(Player.nextRuntimeId(), info);
		}
	}

	public ClientPlayer getPlayer() {
		return player;
	}

	public boolean isConnected() {
		return this.client.getSocket().isConnected() && !this.client.getSocket().isClosed();
	}

	public Client getClient() {
		return client;
	}

	public int getTick() {
		return tick;
	}

	public Logger getLogger() {
		return logger;
	}

	public PacketHandler getPacketHandler() {
		return packetHandler;
	}

	public PacketSender getPacketSender() {
		return packetSender;
	}

	public void setPacketHandler(PacketHandler packetHandler) {
		this.packetHandler = packetHandler;

		this.packetHandlerCaller = new ParameterMethodCaller<>(this.packetHandler);
	}

	public ParameterMethodCaller<Packet> getPacketHandlerCaller() {
		return packetHandlerCaller;
	}

	public void sendDataPacket(DataPacket packet, boolean flush) {
		this.logger.info("Sent: " + packet.getName());
		this.packetSender.sendPacket(packet);

		this.client.getEventEmitter().emit(new DataPacketSendEvent(packet));

		if (flush) {
			try {
				this.packetSender.sendFlush();
			} catch (CompressException e) {
				this.logger.warn("Packet compression failed: " + packet.getName());
				e.printStackTrace();
			} catch (CryptoException e) {
				this.logger.warn("Packet encryption failed: " + packet.getName());
				e.printStackTrace();
			} catch (PacketProcessingException e) {
				this.logger.info("Packet processing failed: " + packet.getName());
				e.printStackTrace();
			}
		}
	}

	protected boolean doClientDisconnect(String reason) {
		if (this.isConnected()) {
			this.client.close();

			if (this.isConnected()) {
				throw new Error("Failed to disconnect");
			}

			this.logger.info("Client ~ : Disconnected.");
			return true;
		}

		return false;
	}

	public void disconnect(String reason) {
		if (this.isConnected()) {
			DisconnectPacket packet = new DisconnectPacket();
			packet.reason = reason;

			this.sendDataPacket(packet, true);
			if (this.doClientDisconnect(reason)) {
				this.logger.info("Client <> Server: Disconnected.");
				return;
			}

			throw new RuntimeException(); // todo:
		}
	}

	public NetworkCipher getCipher() {
		return cipher;
	}

	public void setCipher(NetworkCipher cipher) {
		this.cipher = cipher;
		this.packetSender.setCipher(cipher);
	}

	public void sendDataPacket(DataPacket packet) {
		this.sendDataPacket(packet, false);
	}

	void close() {
		this.logger.info(Colors.wrap("Closing session...", Colors.YELLOW_BOLD_BRIGHT));
		this.packetHandler = null;
		this.packetSender.close(false);
		this.packetSender = null;
		this.logger.info("Closed packet Handler/Sender");

		this.actions.close();
		this.logger.info("Closed network actions");

		try {
			this.client.getSocket().close();
			this.logger.info("Closed socket");
		} catch (IOException e) {
			this.logger.warn("Failed to close socket");
		}

		this.logger.info(Colors.wrap("Successfully closed session", Colors.BLUE_BRIGHT));
	}

	void handlePacket(DataPacket packet) {
		if (!(packet instanceof Clientbound)) {
			this.logger.info("Packet violation (Serverbound): " + packet.getName());
		}

		this.logger.info("Received: " + packet.getName());

		if (packet instanceof Clientbound) {
			try {
				this.packetHandlerCaller.call(packet);
			} catch (IllegalAccessException | InvocationTargetException e) {
				this.disconnect("Internal Client Error (Access, Invocation)");
				e.printStackTrace();
			} catch (PacketHandlingException e) {
				this.disconnect("Packet Processing Error");
				e.printStackTrace();
			} catch (Throwable e) {
				this.disconnect("Internal Client Error (T)");
				e.printStackTrace();
			}

			this.client.getEventEmitter().emit(new DataPacketReceiveEvent(packet));
		}
	}
}
