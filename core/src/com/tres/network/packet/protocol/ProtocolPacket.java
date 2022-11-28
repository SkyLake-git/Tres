package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

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
