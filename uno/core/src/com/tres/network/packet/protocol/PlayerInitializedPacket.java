package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

public class PlayerInitializedPacket extends DataPacket implements Clientbound {

	public int runtimeId;

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.runtimeId = in.readInt();
		this.name = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.runtimeId);
		out.writeUTFString(this.name);
	}

	@Override
	public String getName() {
		return "PlayerInitializedPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_INITIALIZED_PACKET;
	}
}
