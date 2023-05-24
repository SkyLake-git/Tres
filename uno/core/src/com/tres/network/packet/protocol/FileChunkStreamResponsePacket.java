package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FileChunkStreamResponsePacket extends DataPacket implements Serverbound {


	public static final int STATUS_REFUSED = 0;

	public static final int STATUS_ACCEPTED = 1;

	public static final int STATUS_COMPLETED = 2;

	public boolean background;

	public int status;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.status = in.readInt();
		this.background = in.readBoolean();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.status);
		out.writeBoolean(this.background);
	}

	@Override
	public @NotNull String getName() {
		return "FileChunkStreamResponsePacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.FILE_CHUNK_STREAM_RESPONSE_PACKET;
	}
}
