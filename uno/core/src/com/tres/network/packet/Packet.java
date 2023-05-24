package com.tres.network.packet;


import com.tres.network.packet.protocol.ProtocolIds;
import org.jetbrains.annotations.NotNull;

public interface Packet {

	@NotNull String getName();

	void encode(PacketEncoder out) throws PacketProcessingException;

	void decode(PacketDecoder in) throws PacketProcessingException;

	@NotNull ProtocolIds getProtocolId();
}
