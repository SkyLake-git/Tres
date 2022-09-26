package network.packet;

import java.io.IOException;

abstract public class DataPacket implements Packet, Cloneable {


	@Override
	public void decode(PacketDecoder in) {
		this.decodeHeader(in);
		try {
			this.decodePayload(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void decodeHeader(PacketDecoder in) {
		try {
			short header = in.getStream().readShort();
			int pid = header;
			if (pid != this.getProtocolId().id) {
				throw new RuntimeException();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	abstract protected void decodePayload(PacketDecoder in) throws Exception;

	@Override
	public void encode(PacketEncoder out) {
		this.encodeHeader(out);
		try {
			this.encodePayload(out);
		} catch (Exception e) {
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

	abstract protected void encodePayload(PacketEncoder out) throws Exception;

	@Override
	public DataPacket clone() throws CloneNotSupportedException {
		DataPacket a = (DataPacket) super.clone();

		return a;
	}
}
