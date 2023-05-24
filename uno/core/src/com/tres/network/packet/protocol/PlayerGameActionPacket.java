package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.PlayerGameAction;
import com.tres.network.packet.protocol.types.PlayerGameActionFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * プレイヤーに起きたアクションを送信する
 */
public class PlayerGameActionPacket extends DataPacket implements Clientbound, Serverbound {

	public PlayerGameAction action = null;

	public short playerRuntimeId;

	public short targetRuntimeId = -1;

	@Override
	protected void decodePayload(PacketDecoder in) throws InvalidPayloadException, IOException {
		this.action = in.readIf(() -> PlayerGameActionFactory.readAction(in), null);
		this.playerRuntimeId = in.readShort();
		this.targetRuntimeId = in.readNaturalShort();
	}

	@Override
	protected void encodePayload(PacketEncoder out) throws InvalidPayloadException, IOException {
		out.writeIf(action != null, () -> this.action.write(out));
		out.writeShort(this.playerRuntimeId);
		out.writeNaturalNumber(this.targetRuntimeId);
	}

	@Override
	public @NotNull String getName() {
		return "PlayerGameActionPacket";
	}

	@Override
	public @NotNull ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_GAME_ACTION_PACKET;
	}
}
