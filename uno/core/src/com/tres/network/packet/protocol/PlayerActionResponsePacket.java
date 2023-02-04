package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

/**
 * @deprecated
 */
public class PlayerActionResponsePacket extends DataPacket implements Clientbound {
	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {

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
