package com.tres.network.uno.rule;

import com.tres.network.uno.Card;
import com.tres.network.uno.rule.child.PlayStacking;

import java.util.ArrayList;

public class CardRule {

	private final PlayStacking playStacking;

	public CardRule(PlayStacking playStacking) {
		this.playStacking = playStacking;
	}

	public PlayStacking getPlayStacking() {
		return playStacking;
	}

	public boolean canPlay(Card origin, Card.Symbol symbol, Card.Color color) {
		return (origin.symbol.equals(symbol) || origin.color.equals(color) || origin.color.equals(Card.Color.BLACK));
	}

	public boolean canPlay(Card origin, Card surface) {
		return this.canPlay(origin, surface.symbol, surface.color);
	}

	public boolean canStack(Card origin, ArrayList<Card> stack) {
		if (stack.size() == 0) { // ここにおいてよいのか
			return true;
		}

		if (!this.playStacking.isEnabled()) {
			return false;
		}

		Card baseCard = stack.get(0);


		if (stack.size() >= this.playStacking.maxSize()) {
			return false;
		}

		return (this.playStacking.onlySymbolMatch() && origin.symbol.equals(baseCard.symbol)) ||
			   origin.color.equals(baseCard.color) &&
			   (
					   this.playStacking.stackDraws() ||
					   !(baseCard.symbol.orEquals(Card.Symbol.DRAW, Card.Symbol.WILD_DRAW) && origin.symbol.orEquals(Card.Symbol.DRAW, Card.Symbol.WILD_DRAW))
			   ) &&
			   (
					   this.playStacking.stackWilds() ||
					   !(baseCard.symbol.isWild() && origin.symbol.isWild())
			   );
	}
}
