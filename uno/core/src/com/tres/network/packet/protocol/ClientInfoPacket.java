package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ClientInfoPacket extends DataPacket implements Serverbound {

	public boolean hasComputerInfo = false;

	public String remoteAddress;

	public ClientInfo info;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.hasComputerInfo = in.readBoolean();
		this.info = ClientInfo.read(in);
		if (this.hasComputerInfo) {
			this.remoteAddress = in.readUTFString();
		}
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeBoolean(this.hasComputerInfo);
		this.info.write(out);
		if (this.hasComputerInfo) {
			out.writeUTFString(this.remoteAddress);
		}
	}

	@Override
	public @NotNull String getName() {
		return "ClientInfoPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.CLIENT_INFO_PACKET;
	}
}
