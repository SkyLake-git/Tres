package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LoginStatusPacket extends DataPacket implements Clientbound {

	public static final int REQUEST_PROTOCOL = 0;

	public static final int REQUEST_CLIENT_INFO = 1;

	public static final int FINISH = 16;

	public int status;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.status = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.status);
	}

	@Override
	public @NotNull String getName() {
		return "LoginStatusPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.LOGIN_STATUS_PACKET;
	}
}
