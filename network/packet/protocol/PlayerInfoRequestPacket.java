package network.packet.protocol;

import network.packet.DataPacket;
import network.packet.PacketDecoder;
import network.packet.PacketEncoder;
import network.packet.Serverbound;

public class PlayerInfoRequestPacket extends DataPacket implements Serverbound {

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.name = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.name);
	}

	@Override
	public String getName() {
		return "PlayerInfoRequestPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_INFO_REQUEST_PACKET;
	}
}
