package com.tres.network.packet.protocol.types.action;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.protocol.types.PlayerGameAction;

import java.io.IOException;

public class ShoutUnoPlayerGameAction extends PlayerGameAction {
	@Override
	protected void writeContent(PacketEncoder out) throws IOException {

	}

	@Override
	protected void readContent(PacketDecoder in) throws IOException {

	}

	@Override
	public int getId() {
		return PlayerGameAction.SHOUT_UNO;
	}
}
