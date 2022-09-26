package network.uno;

public class Card {

	public enum Color {
		RED("赤"),
		BLUE("青"),
		GREEN("緑"),
		YELLOW("黄"),
		BLACK("黒");

		String trans;

		Color(String trans) {
			this.trans = trans;
		}

		@Override
		public String toString() {
			return this.trans;
		}

		public String toEnglishName() {
			switch (this) {
				case RED -> {
					return "red";
				}
				case BLUE -> {
					return "blue";
				}
				case YELLOW -> {
					return "yellow";
				}
				case BLACK -> {
					return "wild";
				}
				case GREEN -> {
					return "green";
				}
			}

			return "unknown";
		}
	}

	public enum Symbol {
		ZERO(0),
		ONE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		SKIP(10),
		REVERSE(11),
		WILD(12),
		WILD_DRAW(13),
		DRAW(14);

		public int i;

		Symbol(int i) {
			this.i = i;
		}

		public static boolean isWild(Symbol symbol) {
			return (symbol.i == WILD.i) || (symbol.i == WILD_DRAW.i);
		}


		@Override
		public String toString() {
			if (this.i <= 9) {
				return String.valueOf(this.i);
			}

			switch (this) {
				case SKIP -> {
					return "skip";
				}
				case REVERSE -> {
					return "reverse";
				}
				case WILD -> {
					return "color";
				}
				case WILD_DRAW -> {
					return "draw4";
				}
				case DRAW -> {
					return "draw2";
				}
			}

			return "unknown";
		}
	}

	public Symbol symbol;
	public Color color;

	Card(Symbol symbol, Color color) {
		this.symbol = symbol;
		this.color = color;
	}

	boolean canPlay(Card surface) {
		return this.canPlay(surface.symbol, surface.color);
	}

	boolean canPlay(Symbol symbol, Color color) {
		return symbol.i == this.symbol.i && color.trans == this.color.trans;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public Color getColor() {
		return color;
	}
}
