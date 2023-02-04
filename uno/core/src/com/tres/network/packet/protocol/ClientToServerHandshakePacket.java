package com.tres.network.packet.protocol;

import com.tres.network.packet.*;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

/**
 * クライアントで生成した共通鍵をサーバーに送信するためのパケット
 * <p>
 * ServerToClientHandshakePacket のレスポンス
 *
 * @see ServerToClientHandshakePacket
 */
public class ClientToServerHandshakePacket extends DataPacket implements Serverbound {

	/**
	 * 必ず暗号化され、base64エンコードされたSecretKeySpecが格納されている必要があります
	 */
	public SecretKeySpec spec;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		String algorithm = new String(in.readNBytes());
		byte[] key = in.readNBytes();

		this.spec = new SecretKeySpec(key, algorithm);
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeNBytes(this.spec.getAlgorithm());
		out.writeNBytes(new String(this.spec.getEncoded()));
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
