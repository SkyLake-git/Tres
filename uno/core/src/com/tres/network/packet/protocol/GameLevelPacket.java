package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameLevelPacket extends DataPacket implements Clientbound {


	public int gameId;
	public ArrayList<Short> players;
	public short turnPlayer;
	public int turnCount;
	public int deckCards;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.gameId = in.readInt();
		this.players = in.produceArrayList((current) -> in.readShort());

		this.turnPlayer = in.getStream().readShort();
		this.turnCount = in.readInt();
		this.deckCards = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.gameId);

		out.consumeArrayList(this.players, out::writeShort);

		out.getStream().writeShort(this.turnPlayer);
		out.writeInt(this.turnCount);
		out.writeInt(this.deckCards);
	}

	@Override
	public String getName() {
		return "GameLevelPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.GAME_LEVEL_PACKET;
	}
}
