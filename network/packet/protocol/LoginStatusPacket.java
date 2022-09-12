package network.packet.protocol;

import network.packet.Clientbound;
import network.packet.DataPacket;
import network.packet.PacketDecoder;
import network.packet.PacketEncoder;

public class LoginStatusPacket extends DataPacket implements Clientbound {
	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {

	}

	@Override
	public String getName() {
		return null;
	}
}
