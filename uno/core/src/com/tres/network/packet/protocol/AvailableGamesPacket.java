package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.AvailableGameInfo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AvailableGamesPacket extends DataPacket implements Clientbound {

	public Collection<AvailableGameInfo> games = new ArrayList<>();


	public long timestamp;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.games = in.produceArrayList((current) -> AvailableGameInfo.read(in));

		this.timestamp = in.readLong();

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.consumeArrayList(this.games, (i) -> i.write(out));

		out.writeLong(this.timestamp);
	}

	@Override
	public @NotNull String getName() {
		return "AvailableGamesPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.AVAILABLE_GAMES_PACKET;
	}
}
