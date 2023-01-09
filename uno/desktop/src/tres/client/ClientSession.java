package tres.client;


import com.badlogic.gdx.utils.Null;
import tres.client.event.packet.DataPacketReceiveEvent;
import tres.client.event.packet.DataPacketSendEvent;
import tres.client.network.handler.InGamePacketHandler;
import tres.client.network.handler.PacketHandler;
import tres.client.uno.ClientPlayer;
import com.tres.network.packet.*;
import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.cipher.NetworkCipher;
import com.tres.network.packet.compression.CompressException;
import com.tres.network.uno.Player;
import com.tres.utils.Colors;
import com.tres.utils.ParameterMethodCaller;
import com.tres.utils.PrefixedLogger;

import java.io.IOException;

public class ClientSession {

	protected Client client;

	protected int tick;

	protected PrefixedLogger logger;

	protected PacketHandler packetHandler;

	protected PacketSender packetSender;

	protected ClientPlayer player;

	protected ParameterMethodCaller<Packet> packetHandlerCaller;

	protected ClientNetworkActions actions;

	@Null
	protected NetworkCipher cipher;

	ClientSession(Client client) {
		this.client = client;
		this.tick = 0;
		this.cipher = null;
		this.packetHandlerCaller = null;
		this.packetSender = new PacketSender(this.client.getSocket(), new NetworkSettings(true, true));
		this.setPacketHandler(new InGamePacketHandler(this.client, this));
		this.logger = new PrefixedLogger("[ClientSession] ", "Client");
		this.player = null;

		this.actions = new ClientNetworkActions(this);
	}

	public ClientNetworkActions getActions() {
		return actions;
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
		}
	}

	public void createPlayer(PlayerInfo info) {
		if (this.player == null) {
			this.player = new ClientPlayer(Player.nextRuntimeId(), info);
		}
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

	public PrefixedLogger getLogger() {
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
			}
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
			} catch (RuntimeException e) {
				this.logger.emergency("Error occurred when trying to invoke a method");
				this.logger.info(e.toString());
			} catch (Throwable e) {
				this.logger.warn("Error occurred whilst invoking method");
				this.logger.info(e.toString());
			}

			this.client.getEventEmitter().emit(new DataPacketReceiveEvent(packet));
		}
	}
}
