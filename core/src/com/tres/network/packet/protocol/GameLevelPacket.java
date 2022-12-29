package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

import java.util.ArrayList;

public class GameLevelPacket extends DataPacket implements Clientbound {


	public int gameId;
	public ArrayList<Short> players;
	public short turnPlayer;
	public int turnCount;
	public int deckCards;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.gameId = in.readInt();
		this.players = new ArrayList<>();
		int length = in.readInt();
		for (int i = 0; i < length; i++) {
			this.players.add(in.getStream().readShort());
		}

		this.turnPlayer = in.getStream().readShort();
		this.turnCount = in.readInt();
		this.deckCards = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.gameId);
		out.getStream().writeInt(this.players.size());
		for (short runtimeId : this.players) {
			out.getStream().writeShort(runtimeId);
		}

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
