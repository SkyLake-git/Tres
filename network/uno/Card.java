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
			this.trans = trans
		}

		@Override
		public String toString(){
			return this.trans;
		}
	}

	public enum Symbol{
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
		TEN(10),
		SKIP(11),
		REVERSE(12),
		WILD(13),
		WILD_DRAW(14),
		DRAW(15)
		;

		int i;
		Symbol(int i) {
			this.i = i;
		}
	}
}
