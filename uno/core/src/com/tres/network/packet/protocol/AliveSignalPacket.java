package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AliveSignalPacket extends DataPacket implements Clientbound, Serverbound {

	public int isig = 0;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.isig = in.readInt();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.getStream().writeInt(this.isig);
	}

	@Override
	public @NotNull String getName() {
		return "AliveSignalPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.ALIVE_SIGNAL_PACKET;
	}
}
