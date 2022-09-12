package network.packet.protocol;

import network.packet.DataPacket;
import network.packet.PacketDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

public class PacketPool {

	protected HashMap<Integer, DataPacket> pool;

	public PacketPool() {
		this.pool = new HashMap<Integer, DataPacket>();

		this.register(new TextPacket());
	}

	public void register(DataPacket packet) {
		try {
			this.pool.put(packet.NETWORK_ID.id, packet.clone());
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
