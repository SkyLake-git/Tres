package network.packet.protocol;

import network.packet.*;

public class AliveSignalPacket extends DataPacket implements Clientbound, Serverbound {

	public int isig = 0;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.isig = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.getStream().writeInt(this.isig);
	}

	@Override
	public String getName() {
		return "AliveSignalPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.ALIVE_SIGNAL_PACKET;
	}
}
