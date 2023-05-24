package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RequestAvailableGamesPacket extends DataPacket implements Serverbound {

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {

	}

	@Override
	public @NotNull String getName() {
		return "RequestAvailableGamesPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.REQUEST_AVAILABLE_GAMES_PACKET;
	}
}
