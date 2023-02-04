package com.tres.network.packet.protocol;

import com.tres.network.packet.*;
import com.tres.network.packet.protocol.types.PlayerGameAction;
import com.tres.network.packet.protocol.types.PlayerGameActionFactory;

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
		out.writeIf(this.action, action != null, () -> this.action.write(out));
		out.writeShort(this.playerRuntimeId);
		out.writeNaturalNumber(this.targetRuntimeId);
	}

	@Override
	public String getName() {
		return "PlayerGameActionPacket";
	}

	@Override
	public ProtocolIds getProtocolId() {
		return ProtocolIds.PLAYER_GAME_ACTION_PACKET;
	}
}
