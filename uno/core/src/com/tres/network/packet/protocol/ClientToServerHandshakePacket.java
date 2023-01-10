package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;

import javax.crypto.spec.SecretKeySpec;

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
	protected void decodePayload(PacketDecoder in) throws Exception {
		String algorithm = new String(in.readNBytes());
		byte[] key = in.readNBytes();

		this.spec = new SecretKeySpec(key, algorithm);
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
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
