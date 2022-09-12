package network.packet.protocol;

import network.packet.*;

public class TextPacket extends DataPacket implements Serverbound, Clientbound {

	public final ProtocolIds NETWORK_ID = ProtocolIds.TEXT_PACKET;

	public String message = "";

	public String sourceName = "";

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.message = in.readString();
		this.sourceName = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.message);
		out.writeString(this.sourceName);

	}

	@Override
	public String getName() {
		return "TextPacket";
	}
}
