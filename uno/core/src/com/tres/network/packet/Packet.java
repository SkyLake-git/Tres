package com.tres.network.packet;


import com.tres.network.packet.protocol.ProtocolIds;

public interface Packet {

	String getName();

	void encode(PacketEncoder out);

	void decode(PacketDecoder in);

	ProtocolIds getProtocolId();
}
