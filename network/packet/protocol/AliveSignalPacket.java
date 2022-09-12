package network.packet.protocol;

import network.packet.*;

public class AliveSignalPacket extends DataPacket implements Clientbound, Serverbound {

	public long timestamp = 0;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.timestamp = in.readLong();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.getStream().writeLong(this.timestamp);
	}

	@Override
	public String getName() {
		return "AliveSignalPacket";
	}
}
