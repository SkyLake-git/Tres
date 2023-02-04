package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

/**
 * @deprecated
 */
public class PlayerInfoRequestPacket extends DataPacket implements Serverbound {

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.name = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeUTFString(this.name);
	}

	@Override
	public String getName() {
		return "PlayerInfoRequestPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_INFO_REQUEST_PACKET;
	}
}
