package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

import javax.crypto.spec.SecretKeySpec;

public class ClientToServerHandshakePacket extends DataPacket implements Serverbound {

	public SecretKeySpec spec;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		String algorithm = in.readString();
		int keyLength = in.readInt();
		byte[] key = new byte[keyLength];
		for (int i = 0; i < keyLength; i++) {
			key[i] = in.getStream().readByte();
		}

		this.spec = new SecretKeySpec(key, algorithm);
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeString(this.spec.getAlgorithm());
		out.writeInt(this.spec.getEncoded().length);
		out.getStream().writeBytes(new String(this.spec.getEncoded()));
	}

	@Override
	public String getName() {
		return "ClientToServerHandshakePacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.CLIENT_TO_SERVER_HANDSHAKE_PACKET;
	}
}
