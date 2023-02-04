package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

public class AddPlayerPacket extends DataPacket implements Clientbound {

	public PlayerInfo info;
	public short runtimeId;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.info = PlayerInfo.read(in);
		this.runtimeId = in.getStream().readShort();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		this.info.write(out);
		out.getStream().writeShort(this.runtimeId);
	}

	@Override
	public String getName() {
		return "AddPlayerPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.ADD_PLAYER_PACKET;
	}
}
