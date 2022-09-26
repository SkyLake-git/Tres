package network.uno;

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
			switch (symbol) {
				case SKIP, REVERSE, DRAW -> {
					valid = notWild;
				}
				case WILD, WILD_DRAW -> {
					valid = !notWild;
				}
			}

			return valid;
		}

		return true;
	}

	public static boolean validate(Card card) {
		return validate(card.symbol, card.color);
	}

}
