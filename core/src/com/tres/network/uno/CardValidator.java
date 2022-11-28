package com.tres.network.uno;

import java.util.Objects;

public class CardValidator {


	public static boolean validate(Card.Symbol symbol, Card.Color color) {
		if (Card.Symbol.isWild(symbol)) {
			if (!Objects.equals(color.trans, Card.Color.BLACK.trans)) {
				return false;
			}
		}

		if (color.toEnglishName().equals("wild")) {
			if (!Card.Symbol.isWild(symbol)) {
				return false;
			}
		}

		if (symbol.i > 9) {
			boolean notWild = !(color.toEnglishName().equals("wild"));
			boolean valid = true;

			if (symbol.equals(Card.Symbol.SKIP) || symbol.equals(Card.Symbol.REVERSE) || symbol.equals(Card.Symbol.DRAW)) {
				valid = notWild;
			} else if (symbol.equals(Card.Symbol.WILD) || symbol.equals(Card.Symbol.WILD_DRAW)) {
				valid = !notWild;
			}

			return valid;
		}

		return true;
	}

	public static boolean validate(Card card) {
		return validate(card.symbol, card.color);
	}

}
