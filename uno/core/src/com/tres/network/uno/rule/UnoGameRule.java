package com.tres.network.uno.rule;

import com.tres.network.uno.rule.child.GameInitializing;
import com.tres.network.uno.rule.child.PlayerTurn;

public class UnoGameRule {

	protected CardRule cardRule;

	protected PlayerTurn turn;

	protected GameInitializing gameInitializing;

	public UnoGameRule(CardRule cardRule, PlayerTurn turn, GameInitializing gameInitializing) {
		this.cardRule = cardRule;
		this.turn = turn;

		this.gameInitializing = gameInitializing;
	}

	public PlayerTurn getTurn() {
		return turn;
	}

	public CardRule getCardRule() {
		return cardRule;
	}

	public GameInitializing getGameInitializing() {
		return gameInitializing;
	}
}
