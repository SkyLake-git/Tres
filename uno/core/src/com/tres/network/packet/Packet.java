package com.tres.network.packet;


import com.tres.network.packet.protocol.ProtocolIds;

public interface Packet {

	String getName();

	void encode(PacketEncoder out) throws PacketProcessingException;

	void decode(PacketDecoder in) throws PacketProcessingException;

	ProtocolIds getProtocolId();
}
