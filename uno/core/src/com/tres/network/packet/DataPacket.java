package com.tres.network.packet;

import java.io.IOException;

abstract public class DataPacket implements Packet, Cloneable {


	@Override
	public void decode(PacketDecoder in) throws PacketProcessingException {
		try {
			this.decodeHeader(in);
			this.decodePayload(in);
		} catch (InvalidPayloadException e) {
			throw new PacketProcessingException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void decodeHeader(PacketDecoder in) throws IncorrectProtocolIdException {
		try {
			int pid = in.getStream().readShort();
			if (pid != this.getProtocolId().id) {
				throw new IncorrectProtocolIdException();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	abstract protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException;

	@Override
	public void encode(PacketEncoder out) throws PacketProcessingException {
		this.encodeHeader(out);
		try {
			this.encodePayload(out);
		} catch (InvalidPayloadException e) {
			throw new PacketProcessingException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void encodeHeader(PacketEncoder out) {
		try {
			out.getStream().writeShort(this.getProtocolId().id);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	abstract protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException;

	@Override
	public DataPacket clone() throws CloneNotSupportedException {

		return (DataPacket) super.clone();
	}
}
