package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

public class PlayerInfoRequestPacket extends DataPacket implements Serverbound {

	public String name;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.name = in.readString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.name);
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
