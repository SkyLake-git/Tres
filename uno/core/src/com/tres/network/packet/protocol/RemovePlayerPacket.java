package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RemovePlayerPacket extends DataPacket implements Clientbound {

	public short runtimeId;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {

	}

	@Override
	public @NotNull String getName() {
		return "RemovePlayerPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.REMOVE_PLAYER_PACKET;
	}
}
