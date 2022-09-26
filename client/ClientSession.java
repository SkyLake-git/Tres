package client;

import client.network.handler.InGamePacketHandler;
import client.network.handler.PacketHandler;
import client.uno.ClientPlayer;
import network.packet.Clientbound;
import network.packet.DataPacket;
import network.packet.PacketSender;
import network.packet.protocol.*;
import network.uno.Player;
import utils.Colors;
import utils.PrefixedLogger;

import java.io.IOException;

public class ClientSession {

	protected Client client;

	protected int tick;

	protected PrefixedLogger logger;

	protected PacketHandler packetHandler;

	protected PacketSender packetSender;

	protected ClientPlayer player;

	ClientSession(Client client) {
		this.client = client;
		this.tick = 0;
		this.packetSender = new PacketSender(this.client.getSocket());
		this.packetHandler = new InGamePacketHandler(this.client, this);
		this.logger = new PrefixedLogger("[ClientSession] ", "Client");

		this.player = null;
	}

	void tick() {
		this.tick++;

		if (!this.isConnected()) {
			throw new RuntimeException("socket not connected");
		}

		this.packetSender.tick();
	}

	public void createPlayer(String name) {
		if (this.player == null) {
			this.player = new ClientPlayer(Player.nextRuntimeId(), name);
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

	void close() {
		this.logger.info(Colors.wrap("Closing session...", Colors.YELLOW_BOLD_BRIGHT));
		this.packetHandler = null;
		this.packetSender.close(false);
		this.logger.info("Closed packet Handler/Sender");

		try {
			this.client.getSocket().close();
			this.logger.info("Closed socket");
		} catch (IOException e) {
			this.logger.warning("Failed to close socket");
		}

		this.logger.info(Colors.wrap("Successfully closed session", Colors.BLUE_BRIGHT));
	}

	void handlePacket(DataPacket packet) {
		if (!(packet instanceof Clientbound)) {
			this.logger.info("Packet violation (Serverbound): " + packet.getName());
		}

		this.logger.info("Received: " + packet.getName());

		if (packet instanceof TextPacket) {
			this.packetHandler.handleText((TextPacket) packet);
		} else if (packet instanceof DisconnectPacket) {
			this.packetHandler.handleDisconnect((DisconnectPacket) packet);
		} else if (packet instanceof AliveSignalPacket) {
			this.packetHandler.handleAliveSignal((AliveSignalPacket) packet);
		} else if (packet instanceof LoginStatusPacket) {
			this.packetHandler.handleLoginStatus((LoginStatusPacket) packet);
		} else if (packet instanceof PlayerInitializedPacket) {
			this.packetHandler.handlePlayerInitialized((PlayerInitializedPacket) packet);
		}
	}
}
