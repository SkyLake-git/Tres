package com.tres.network.packet.protocol;

import com.tres.network.packet.Clientbound;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.protocol.types.AvailableGameInfo;

import java.util.ArrayList;

public class AvailableGamesPacket extends DataPacket implements Clientbound {

	public ArrayList<AvailableGameInfo> games = new ArrayList<>();


	public long timestamp;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		int length = in.readInt();

		for (int i = 0; i < length; i++) {
			this.games.add(AvailableGameInfo.read(in));
		}


		this.timestamp = in.readLong();

	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.games.size());

		for (AvailableGameInfo gameInfo : this.games) {
			gameInfo.write(out);
		}

		out.writeLong(this.timestamp);
	}

	@Override
	public String getName() {
		return "AvailableGamesPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.AVAILABLE_GAMES_PACKET;
	}
}
