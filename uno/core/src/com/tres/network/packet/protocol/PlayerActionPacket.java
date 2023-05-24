package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.PlayerAction;
import com.tres.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PlayerActionPacket extends DataPacket implements Serverbound {

	public PlayerAction action;

	public int gameId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		int actionOrdinal = in.readInt();
		this.action = Utils.getEnumOrdinal(PlayerAction.class, actionOrdinal);

		boolean hasGameId = in.readBoolean();
		if (hasGameId) {
			this.gameId = in.readInt();
		}
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeInt(this.action.ordinal());

		boolean hasGameId = this.gameId > -1;
		out.writeBoolean(hasGameId);
		if (hasGameId) {
			out.writeInt(this.gameId);
		}
	}

	@Override
	public @NotNull String getName() {
		return "PlayerActionPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_ACTION_PACKET;
	}
}
