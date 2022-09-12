package network.packet;

import java.io.IOException;
import java.io.InputStream;

public class InternalInputStream extends InputStream {
	public byte[] buf;
	protected int pos;

	protected int count;

	InternalInputStream(int size) {
		this.buf = new byte[size];
		this.pos = 0;
		this.count = this.buf.length;
	}

	@Override
	public int read() throws IOException {
		return (pos < count) ? (buf[pos++] & 0xff) : -1;
	}
}
