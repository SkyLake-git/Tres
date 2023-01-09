package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PacketPool {

	protected HashMap<Integer, Constructor<? extends DataPacket>> pool;

	public PacketPool() {
		this.pool = new HashMap<>();

		this.register(new TextPacket());
		this.register(new DisconnectPacket());
		this.register(new AliveSignalPacket());
		this.register(new LoginStatusPacket());
		this.register(new LoginPacket());
		this.register(new ClientInfoPacket());
		this.register(new PlayerInfoRequestPacket());
		this.register(new PlayerInitializedPacket());
		this.register(new GameEventPacket());
		this.register(new GameLevelPacket());
		this.register(new AddPlayerPacket());
		this.register(new CardActionPacket());
		this.register(new RequestAvailableGamesPacket());
		this.register(new AvailableGamesPacket());
		this.register(new PlayerActionPacket());
		this.register(new PlayerActionResponsePacket());
		this.register(new ClientToServerHandshakePacket());
		this.register(new ServerToClientHandshakePacket());
	}

	public void register(DataPacket packet) {
		try {
			// System.out.println("Packet Registered: ID: " + packet.clone().getProtocolId().id + " Packet: " + packet.getName());
			this.pool.put(packet.getProtocolId().id, packet.getClass().getConstructor());
		} catch (NoSuchMethodException e) {

		}
	}

	public DataPacket getPacketById(int id) {
		if (this.pool.containsKey(id)) {
			try {
				return this.pool.get(id).newInstance();
			} catch (InvocationTargetException | InstantiationException |
					 IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return null;
	}

	public DataPacket getPacket(byte[] buffer) {
		DataInputStream stream = new DataInputStream(new ByteArrayInputStream(buffer.clone()));
		try {
			int pid = stream.readShort();
			return this.getPacketById(pid);
		} catch (IOException e) {
			return null;
		}
	}

	public DataPacket getPacketFull(byte[] buffer) {
		DataPacket packet = this.getPacket(buffer);
		if (packet != null) {
			PacketDecoder in = new PacketDecoder(buffer);
			try {
				packet.decode(in);
			} catch (Exception e) {
				return null;
			}

			return packet;
		}

		return null;
	}
}
