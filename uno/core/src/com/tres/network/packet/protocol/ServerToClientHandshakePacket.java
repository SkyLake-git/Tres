package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

public class ServerToClientHandshakePacket extends DataPacket implements Clientbound {

	public String jwtToken;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.jwtToken = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.jwtToken);
	}

	@Override
	public String getName() {
		return "ServerToClientHandshakePacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.SERVER_TO_CLIENT_HANDSHAKE_PACKET;
	}
}
