package com.tres.client;


import com.tres.client.network.handler.InGamePacketHandler;
import com.tres.client.network.handler.PacketHandler;
import com.tres.client.uno.ClientPlayer;
import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.NetworkSettings;
import com.tres.network.packet.PacketSender;
import com.tres.network.packet.compression.CompressException;
import com.tres.network.uno.Player;
import com.tres.utils.Colors;
import com.tres.utils.PrefixedLogger;

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
		this.packetSender = new PacketSender(this.client.getSocket(), new NetworkSettings(true));
		this.packetHandler = new InGamePacketHandler(this.client, this);
		this.logger = new PrefixedLogger("[ClientSession] ", "Client");

		this.player = null;
	}

	void tick() {
		this.tick++;

		if (!this.isConnected()) {
			throw new RuntimeException("socket not connected");
		}

		try {
			this.packetSender.tick();
		} catch (CompressException e) {
			this.logger.info("Packet compression failed: tick flush");
			this.client.close();
		}
	}

	public void createPlayer(String name) {
		if (this.player == null) {
			this.player = new ClientPlayer(Player.nextRuntimeId(), name);
		}
	}

	public void fetchGameList() {
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


	public void sendDataPacket(DataPacket packet, boolean flush) {
		this.packetSender.sendPacket(packet);

		if (flush) {
			try {
				this.packetSender.sendFlush();
			} catch (CompressException e) {
				this.logger.warn("Packet compression failed: " + packet.getName());
			}
		}
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
			this.packetHandler.handle((Clientbound) packet);
		}
	}
}
