package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

public class AddPlayerPacket extends DataPacket implements Clientbound {

	public String name;
	public short runtimeId;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.name = in.readUTFString();
		this.runtimeId = in.getStream().readShort();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeUTFString(this.name);
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
