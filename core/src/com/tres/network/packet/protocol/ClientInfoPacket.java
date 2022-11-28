package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

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
