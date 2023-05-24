package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DisconnectPacket extends DataPacket implements Clientbound, Serverbound {

	public String reason = "";

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.reason = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeUTFString(this.reason);
	}

	@Override
	public @NotNull String getName() {
		return "DisconnectPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.DISCONNECT_PACKET;
	}
}
