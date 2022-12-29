package com.tres.network.packet.protocol;

import com.tres.network.packet.DataPacket;
import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.packet.Serverbound;
import com.tres.network.packet.protocol.types.PlayerAction;

public class PlayerActionPacket extends DataPacket implements Serverbound {

	public PlayerAction action;

	public int gameId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws Exception {
		int actionValue = in.readInt();
		this.action = PlayerAction.actionOf(actionValue);
		if (this.action == null) {
			throw new Exception("Action \"" + actionValue + "\" not found");
		}

		boolean hasGameId = in.readBoolean();
		if (hasGameId) {
			this.gameId = in.readInt();
		}
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws Exception {
		out.writeInt(this.action.id);

		boolean hasGameId = this.gameId > -1;
		out.writeBoolean(hasGameId);
		if (hasGameId) {
			out.writeInt(this.gameId);
		}
	}

	@Override
	public String getName() {
		return "PlayerActionPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_ACTION_PACKET;
	}
}
