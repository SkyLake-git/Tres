package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

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
	public @NotNull String getName() {
		return "TextPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.TEXT_PACKET;
	}
}
