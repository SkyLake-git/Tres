package network.packet.protocol;

import network.packet.*;

public class DisconnectPacket extends DataPacket implements Clientbound, Serverbound {

	public String reason = "";

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.reason = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.reason);
	}

	@Override
	public String getName() {
		return "DisconnectPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.DISCONNECT_PACKET;
	}
}
