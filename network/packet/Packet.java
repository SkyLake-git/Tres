package network.packet;

import network.packet.protocol.ProtocolIds;

public interface Packet {

	String getName();

	void encode(PacketEncoder out);

	void decode(PacketDecoder in);

	ProtocolIds getProtocolId();
}
