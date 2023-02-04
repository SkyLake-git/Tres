package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

public class ClientInfoPacket extends DataPacket implements Serverbound {

	public String remoteAddress;

	public ClientInfo info;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.remoteAddress = in.readUTFString();
		this.info = ClientInfo.read(in);
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeUTFString(this.remoteAddress);
		this.info.write(out);
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
