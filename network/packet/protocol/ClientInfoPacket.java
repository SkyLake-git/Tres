package network.packet.protocol;

import network.packet.DataPacket;
import network.packet.PacketDecoder;
import network.packet.PacketEncoder;
import network.packet.Serverbound;

public class ClientInfoPacket extends DataPacket implements Serverbound {

	public String remoteAddress;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.remoteAddress = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.remoteAddress);
	}

	@Override
	public String getName() {
		return "ClientInfoPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.CLIENT_INFO_PACKET;
	}
}
