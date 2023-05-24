package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PlayerInitializedPacket extends DataPacket implements Clientbound {

	public short runtimeId;

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.runtimeId = in.readShort();
		this.name = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeShort(this.runtimeId);
		out.writeUTFString(this.name);
	}

	@Override
	public @NotNull String getName() {
		return "PlayerInitializedPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_INITIALIZED_PACKET;
	}
}
