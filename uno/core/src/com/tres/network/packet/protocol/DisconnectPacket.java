package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

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
