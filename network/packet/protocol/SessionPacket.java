package network.packet.protocol;

import network.packet.DataPacket;
import network.packet.PacketDecoder;
import network.packet.PacketEncoder;
import network.packet.Serverbound;

public class SessionPacket extends DataPacket implements Serverbound {

	long timestamp = 0;

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
		return "SessionPacket";
	}
}
