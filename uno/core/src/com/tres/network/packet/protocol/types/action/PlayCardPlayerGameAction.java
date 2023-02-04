package com.tres.network.packet.protocol.types.action;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.protocol.types.PlayerGameAction;

import java.io.IOException;
import java.util.ArrayList;

public class PlayCardPlayerGameAction extends PlayerGameAction {

	public ArrayList<Integer> cards;

	@Override
	protected void writeContent(PacketEncoder out) throws IOException {
		out.consumeArrayList(this.cards, out::writeInt);
	}

	@Override
	protected void readContent(PacketDecoder in) throws IOException {
		this.cards = in.produceArrayList((current) -> in.readInt());
	}

	@Override
	public int getId() {
		return PlayerGameAction.PLAY_CARD;
	}
}
