package network.packet.protocol;

import network.packet.DataPacket;
import network.packet.PacketDecoder;
import network.packet.PacketEncoder;
import network.packet.Serverbound;

public class ProtocolPacket extends DataPacket implements Serverbound {

	public int protocol = ProtocolIds.PROTOCOL;
	public int version = ProtocolIds.VERSION;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.protocol = in.readInt();
		this.version = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.protocol);
		out.writeInt(this.version);
	}

	@Override
	public String getName() {
		return "ProtocolPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PROTOCOL_PACKET;
	}
}
