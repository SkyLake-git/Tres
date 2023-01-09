package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

public class LoginPacket extends DataPacket implements Serverbound {

	public int protocol = ProtocolIds.PROTOCOL;
	public int version = ProtocolIds.VERSION;

	public String jwtToken = "";

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.protocol = in.readInt();
		this.version = in.readInt();
		this.jwtToken = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.protocol);
		out.writeInt(this.version);
		out.writeString(this.jwtToken);
	}

	@Override
	public String getName() {
		return "ProtocolPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.LOGIN_PACKET;
	}
}
