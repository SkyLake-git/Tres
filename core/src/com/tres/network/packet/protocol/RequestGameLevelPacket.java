package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

public class RequestGameLevelPacket extends DataPacket implements Serverbound {

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {

	}

	@Override
	public String getName() {
		return "RequestGameLevelPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.REQUEST_GAME_LEVEL_PACKET;
	}
}
