package network.packet.protocol;

public enum ProtocolIds {
	UNKNOWN(-1),
	TEXT_PACKET(1);

	public int id;

	ProtocolIds(int id) {
		this.id = id;
	}
}
