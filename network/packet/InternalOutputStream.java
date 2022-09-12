package network.packet;

import java.io.IOException;
import java.io.OutputStream;

public class InternalOutputStream extends OutputStream {
	protected byte[] buf;

	protected int count;


	InternalOutputStream(int size) {
		this.buf = new byte[size];
	}

	public byte[] getBuffer() {
		return buf;
	}

	@Override
	public void write(int b) throws IOException {
		buf[count++] = (byte) b;
	}
}
