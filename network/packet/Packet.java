package network.packet;

public interface Packet {

	String getName();

	void encode(PacketEncoder out);

	void decode(PacketDecoder in);
}
