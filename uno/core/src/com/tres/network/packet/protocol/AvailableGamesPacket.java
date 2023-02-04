package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.AvailableGameInfo;

import java.io.IOException;
import java.util.ArrayList;

public class AvailableGamesPacket extends DataPacket implements Clientbound {

	public ArrayList<AvailableGameInfo> games = new ArrayList<>();


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
	public String getName() {
		return "AvailableGamesPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.AVAILABLE_GAMES_PACKET;
	}
}
