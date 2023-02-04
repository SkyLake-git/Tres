package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.Rank;

import java.io.IOException;
import java.util.ArrayList;

public class GameResultPacket extends DataPacket implements Clientbound {

	public ArrayList<Rank> ranking;
	public int gameId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.ranking = in.produceArrayList((current) -> Rank.read(in));
		this.gameId = in.readNaturalInteger();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.consumeArrayList(this.ranking, (rank) -> rank.write(out));
		out.writeNaturalNumber(this.gameId);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.GAME_RESULT_PACKET;
	}
}
