package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import java.io.IOException;

/**
 * 公開鍵などの情報をクライアントに送信するためのパケット
 * @see ClientToServerHandshakePacket
 */
public class ServerToClientHandshakePacket extends DataPacket implements Clientbound {

	public String jwtToken;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.jwtToken = in.readUTFString();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeUTFString(this.jwtToken);
	}

	@Override
	public String getName() {
		return "ServerToClientHandshakePacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.SERVER_TO_CLIENT_HANDSHAKE_PACKET;
	}
}
