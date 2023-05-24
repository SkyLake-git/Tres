package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FileStreamRequestPacket extends DataPacket implements Serverbound {

	public String identifier;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.identifier = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeUTFString(this.identifier);
	}

	@Override
	public @NotNull String getName() {
		return "FileStreamRequestPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.FILE_STREAM_REQUEST_PACKET;
	}
}
