package com.tres.network.packet.protocol.types;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;

import java.io.IOException;

abstract public class PlayerGameAction {

	public static final int PLAY_CARD = 0;
	public static final int DRAW_CARD = 1;
	public static final int SKIP_TURN = 2;
	public static final int SHOUT_UNO = 3;
	public static final int UNO_ACCUSATION = 4;

	protected abstract void writeContent(PacketEncoder out) throws IOException;

	protected abstract void readContent(PacketDecoder in) throws IOException;

	public void write(PacketEncoder out) throws IOException {
		out.writeInt(this.getId());
		this.writeContent(out);
	}

	public void read(PacketDecoder in) throws IOException {
		this.readContent(in);
	}

	abstract public int getId();
}
