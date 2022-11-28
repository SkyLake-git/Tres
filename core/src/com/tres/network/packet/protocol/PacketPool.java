package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

public class PacketPool {

	protected HashMap<Integer, DataPacket> pool;

	public PacketPool() {
		this.pool = new HashMap<Integer, DataPacket>();

		this.register(new TextPacket());
		this.register(new DisconnectPacket());
		this.register(new AliveSignalPacket());
		this.register(new LoginStatusPacket());
		this.register(new ProtocolPacket());
		this.register(new ClientInfoPacket());
		this.register(new PlayerInfoRequestPacket());
		this.register(new PlayerInitializedPacket());
		this.register(new GameEventPacket());
		this.register(new GameLevelPacket());
		this.register(new AddPlayerPacket());
		this.register(new CardActionPacket());
		this.register(new RequestGameLevelPacket());
	}

	public void register(DataPacket packet) {
		try {
			// System.out.println("Packet Registered: ID: " + packet.clone().getProtocolId().id + " Packet: " + packet.getName());
			this.pool.put(packet.getProtocolId().id, packet.clone());
		} catch (CloneNotSupportedException e) {

		}
	}

	public DataPacket getPacketById(int id) {
		if (this.pool.containsKey(id)) {
			return this.pool.get(id);
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
