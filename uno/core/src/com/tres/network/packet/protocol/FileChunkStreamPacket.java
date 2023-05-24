package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.FileStreamInfo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FileChunkStreamPacket extends DataPacket implements Clientbound {

	public FileStreamInfo info;

	public boolean requestBackground;

	public boolean requestOverwrite;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.info = new FileStreamInfo(
				in.readInt(),
				in.readInt(),
				in.readUTFString(),
				in.readUTFString()
		);
		this.requestBackground = in.readBoolean();
		this.requestOverwrite = in.readBoolean();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.info.maxChunkSize);
		out.writeInt(this.info.chunkCount);
		out.writeUTFString(this.info.digest);
		out.writeUTFString(this.info.identifier);
		out.writeBoolean(this.requestBackground);
		out.writeBoolean(this.requestOverwrite);
	}

	@Override
	public @NotNull String getName() {
		return "FileChunkStreamPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.FILE_CHUNK_STREAM_PACKET;
	}
}
