package network.packet.protocol;

import network.packet.Clientbound;
import network.packet.DataPacket;
import network.packet.PacketDecoder;
import network.packet.PacketEncoder;

public class PlayerInitializedPacket extends DataPacket implements Clientbound {

	public int runtimeId;

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.runtimeId = in.readInt();
		this.name = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.runtimeId);
		out.writeString(this.name);
	}

	@Override
	public String getName() {
		return "PlayerInitializedPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_INITIALIZED_PACKET;
	}
}
