package com.tres.network.packet.protocol.types;

import com.tres.network.packet.PacketDecoder;
import com.tres.network.packet.PacketEncoder;
import com.tres.network.uno.rule.CardRule;
import com.tres.network.uno.rule.UnoGameRule;
import com.tres.network.uno.rule.child.GameInitializing;
import com.tres.network.uno.rule.child.PlayStacking;
import com.tres.network.uno.rule.child.PlayerTurn;

import java.io.IOException;

public class GameLevelRule {

	protected UnoGameRule unoRule;

	public GameLevelRule(UnoGameRule unoRule) {
		this.unoRule = unoRule;
	}

	public static GameLevelRule readFrom(PacketDecoder in) throws IOException {
		return new GameLevelRule(readUnoRule(in));
	}

	protected static void writeUnoRule(PacketEncoder out, UnoGameRule r) throws IOException {
		writeCardRule(out, r.getCardRule());
		writeTurnRule(out, r.getTurn());
		writeGameInitializingRule(out, r.getGameInitializing());
	}

	protected static UnoGameRule readUnoRule(PacketDecoder in) throws IOException {
		return new UnoGameRule(readCardRule(in), readTurnRule(in), readGameInitializingRule(in));
	}

	protected static void writeCardRule(PacketEncoder out, CardRule r) throws IOException {
		PlayStacking c = r.getPlayStacking();

		out.writeIf(c.isEnabled(), () -> {
			out.writeBoolean(c.onlySymbolMatch());
			out.writeInt(c.maxSize());
			out.writeBoolean(c.stackDraws());
			out.writeBoolean(c.stackWilds());
		});
	}

	protected static CardRule readCardRule(PacketDecoder in) throws IOException {
		PlayStacking c = in.readIf(
				() -> PlayStacking.create(in.readBoolean(), in.readInt(), in.readBoolean(), in.readBoolean()),
				PlayStacking.disabled()
		);

		return new CardRule(c);
	}

	protected static void writeTurnRule(PacketEncoder out, PlayerTurn r) throws IOException {
		out.writeDouble(r.timeLimit());
	}

	protected static PlayerTurn readTurnRule(PacketDecoder in) throws IOException {
		return PlayerTurn.create(in.readDouble());
	}

	protected static void writeGameInitializingRule(PacketEncoder out, GameInitializing r) throws IOException {
		out.writeInt(r.cards());
	}

	protected static GameInitializing readGameInitializingRule(PacketDecoder in) throws IOException {
		return GameInitializing.create(in.readInt());
	}

	public UnoGameRule getUnoRule() {
		return unoRule;
	}

	public void write(PacketEncoder out) throws IOException {
		writeUnoRule(out, this.unoRule);
	}
}
