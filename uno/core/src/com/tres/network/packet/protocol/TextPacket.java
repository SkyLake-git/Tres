package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

public class TextPacket extends DataPacket implements Serverbound, Clientbound {

	public String message = "";

	public String sourceName = "";

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.message = in.readUTFString();
		this.sourceName = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeUTFString(this.message);
		out.writeUTFString(this.sourceName);

	}

	@Override
	public String getName() {
		return "TextPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.TEXT_PACKET;
	}
}
