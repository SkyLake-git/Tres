package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

public class LoginStatusPacket extends DataPacket implements Clientbound {

	public static final int REQUEST_PROTOCOL = 0;
	public static final int REQUEST_CLIENT_INFO = 1;
	public static final int FINISH = 16;

	public int status;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		this.status = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.status);
	}

	@Override
	public String getName() {
		return "LoginStatusPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.LOGIN_STATUS_PACKET;
	}
}
