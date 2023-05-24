package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FileStreamResultPacket extends DataPacket implements Serverbound {

	public boolean digestMismatch;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.digestMismatch = in.readBoolean();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeBoolean(this.digestMismatch);
	}

	@Override
	public @NotNull String getName() {
		return "FileStreamResultPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.FILE_STREAM_RESULT_PACKET;
	}
}
