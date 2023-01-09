package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

public class PlayerActionResponsePacket extends DataPacket implements Clientbound {
	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {

	}

	@Override
	public String getName() {
		return "PlayerActionResponsePacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_ACTION_RESPONSE_PACKET;
	}
}
