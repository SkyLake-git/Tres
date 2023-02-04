package com.tres.network.packet;

import com.tres.network.packet.protocol.PacketPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PacketBatch {

	protected ByteArrayOutputStream buffer;

	PacketBatch() {
		this.buffer = new ByteArrayOutputStream();
	}

	public void write(byte[] payload){
		try {
			this.buffer.write(payload);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static PacketBatch from(ArrayList<byte[]> payloads) {
		PacketBatch batch = new PacketBatch();

		for (byte[] payload : payloads){
			batch.write(payload);
		}

		return batch;
	}

	public static PacketBatch from(byte[] payload){
		PacketBatch batch = new PacketBatch();
		batch.write(payload);

		return batch;
	}

	public ArrayList<Packet> getPackets(PacketPool packetPool, int max) throws IOException, PacketProcessingException {
		ArrayList<Packet> packets = new ArrayList<>();
		int offset = 0;
		ByteBuffer buffer = ByteBuffer.wrap(this.getBuffer());

		for (int i = 0; i < max; i ++){
			byte[] buf = new byte[buffer.remaining()];
			buffer.get(buf);

			DataPacket packet = packetPool.getPacket(buf);

			PacketDecoder decoder = new PacketDecoder(buf);
			int beforeAvailable = decoder.getStream().available();
			packet.decode(decoder);
			int afterAvailable = decoder.getStream().available();

			int usedBytes = beforeAvailable - afterAvailable;

			offset += usedBytes;
			buffer.position(offset);

			packets.add(packet);

			if (buffer.remaining() == 0){
				break;
			}

			// fixme: 異常なパケットがあると例外がスローされて他のパケットはデコードしようとしない
		}

		return packets;
	}

	public byte[] getBuffer() {
		return this.buffer.toByteArray();
	}
}
