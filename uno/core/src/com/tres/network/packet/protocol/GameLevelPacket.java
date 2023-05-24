package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.GameLevelRule;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class GameLevelPacket extends DataPacket implements Clientbound {


	public int gameId;

	public ArrayList<Short> players;

	public short turnPlayer;

	public int turnCount;

	public int deckCards;

	public GameLevelRule rule;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.gameId = in.readInt();
		this.players = in.produceArrayList((current) -> in.readShort());
		this.turnPlayer = in.getStream().readShort();
		this.turnCount = in.readInt();
		this.deckCards = in.readInt();
		this.rule = GameLevelRule.readFrom(in);
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.gameId);
		out.consumeArrayList(this.players, out::writeShort);
		out.getStream().writeShort(this.turnPlayer);
		out.writeInt(this.turnCount);
		out.writeInt(this.deckCards);
		this.rule.write(out);
	}

	@Override
	public @NotNull String getName() {
		return "GameLevelPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.GAME_LEVEL_PACKET;
	}
}
