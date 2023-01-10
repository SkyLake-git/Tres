package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

public class PlayerInitializedPacket extends DataPacket implements Clientbound {

	public int runtimeId;

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.runtimeId = in.readInt();
		this.name = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
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
