package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FileChunkDataPacket extends DataPacket implements Clientbound {

	public int chunkIndex;

	public byte[] chunkPayload;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.chunkIndex = in.readInt();
		this.chunkPayload = in.readN();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.chunkIndex);
		out.writeN(this.chunkPayload);
	}

	@Override
	public @NotNull String getName() {
		return "FileChunkDataPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.FILE_CHUNK_DATA_PACKET;
	}
}
