package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LoginPacket extends DataPacket implements Serverbound {

	public int protocol = ProtocolIds.PROTOCOL;

	public int version = ProtocolIds.VERSION;

	public String jwtToken = "";

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.protocol = in.readInt();
		this.version = in.readInt();
		this.jwtToken = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.protocol);
		out.writeInt(this.version);
		out.writeUTFString(this.jwtToken);
	}

	@Override
	public @NotNull String getName() {
		return "LoginPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.LOGIN_PACKET;
	}
}
