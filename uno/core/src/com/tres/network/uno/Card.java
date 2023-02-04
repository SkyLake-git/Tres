package com.tres.network.uno;

import com.tres.network.uno.rule.CardRule;
import com.tres.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Card {

	private static HashMap<Symbol, ArrayList<Color>> validPatterns = null;

	private static HashMap<Symbol, ArrayList<Color>> creatablePatterns = null;


	static {
		validPatterns = new HashMap<>();
		creatablePatterns = new HashMap<>();
		ArrayList<Color> normalColors = Utils.toArrayList(new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW});
		ArrayList<Color> wildColor = new ArrayList<>();

		wildColor.add(Color.BLACK);

		validPatterns.put(Symbol.ZERO, normalColors);
		validPatterns.put(Symbol.ONE, normalColors);
		validPatterns.put(Symbol.TWO, normalColors);
		validPatterns.put(Symbol.THREE, normalColors);
		validPatterns.put(Symbol.FOUR, normalColors);
		validPatterns.put(Symbol.FIVE, normalColors);
		validPatterns.put(Symbol.SIX, normalColors);
		validPatterns.put(Symbol.SEVEN, normalColors);
		validPatterns.put(Symbol.EIGHT, normalColors);
		validPatterns.put(Symbol.NINE, normalColors);
		validPatterns.put(Symbol.SKIP, normalColors);
		validPatterns.put(Symbol.REVERSE, normalColors);
		validPatterns.put(Symbol.WILD, Utils.toArrayList(new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK}));
		validPatterns.put(Symbol.WILD_DRAW, wildColor);
		validPatterns.put(Symbol.DRAW, normalColors);

		creatablePatterns.put(Symbol.ZERO, normalColors);
		creatablePatterns.put(Symbol.ONE, normalColors);
		creatablePatterns.put(Symbol.TWO, normalColors);
		creatablePatterns.put(Symbol.THREE, normalColors);
		creatablePatterns.put(Symbol.FOUR, normalColors);
		creatablePatterns.put(Symbol.FIVE, normalColors);
		creatablePatterns.put(Symbol.SIX, normalColors);
		creatablePatterns.put(Symbol.SEVEN, normalColors);
		creatablePatterns.put(Symbol.EIGHT, normalColors);
		creatablePatterns.put(Symbol.NINE, normalColors);
		creatablePatterns.put(Symbol.SKIP, normalColors);
		creatablePatterns.put(Symbol.REVERSE, normalColors);
		creatablePatterns.put(Symbol.WILD, wildColor);
		creatablePatterns.put(Symbol.WILD_DRAW, wildColor);
		creatablePatterns.put(Symbol.DRAW, normalColors);
	}

	public static ArrayList<Card> getCards(CardRule rule) {
		ArrayList<Card> allPattern = new ArrayList<>();

		// moved from old(python) code
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			numbers.addAll(IntStream.range(1, 9 + 1).boxed().collect(Collectors.toList()));
		}
		numbers.add(0);

		// reverse / skip / draw x2
		numbers.addAll(Arrays.stream(new int[]{14, 14, 11, 11, 10, 10}).boxed().collect(Collectors.toList()));

		// wild draw/change color
		numbers.addAll(Arrays.stream(new int[]{12, 12, 12, 12, 13, 13}).boxed().collect(Collectors.toList()));

		for (int symbolOrdinal : numbers) {
			Symbol symbol = Utils.getEnumOrdinal(Symbol.class, symbolOrdinal);

			for (Color color : creatablePatterns.get(symbol)) {
				allPattern.add(new Card(symbol, color));
			}
		}

		return allPattern;
	}

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
			if (this.equals(RED)) {
				return "red";
			} else if (this.equals(BLUE)) {
				return "blue";
			} else if (this.equals(GREEN)) {
				return "green";
			} else if (this.equals(YELLOW)) {
				return "yellow";
			} else if (this.equals(BLACK)) {
				return "black";
			}

			return "unknown";
		}

		public boolean isValid() {
			return !this.toEnglishName().equals("unknown");
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

		public boolean isWild() {
			return this.orEquals(Symbol.WILD, Symbol.WILD_DRAW);
		}

		public boolean orEquals(Symbol... symbols) {
			for (Symbol t : symbols) {
				if (this.equals(t)) {
					return true;
				}
			}

			return false;
		}

		public static Symbol fromNumber(int number) {
			if (number > 9) {
				throw new RuntimeException("number must lower than 9");
			}

			return values()[number];
		}

		@Override
		public String toString() {
			if (this.i <= 9) {
				return String.valueOf(this.i);
			}

			if (this.equals(SKIP)) {
				return "skip";
			} else if (this.equals(REVERSE)) {
				return "reverse";
			} else if (this.equals(WILD)) {
				return "color";
			} else if (this.equals(WILD_DRAW)) {
				return "draw4";
			} else if (this.equals(DRAW)) {
				return "draw";
			}


			return "unknown";
		}

		public boolean isValid() {
			return true; // todo: always return true
		}
	}

	public Symbol symbol;
	public Color color;

	public Card(Symbol symbol, Color color) {
		this.symbol = symbol;
		this.color = color;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public Color getColor() {
		return color;
	}

	public boolean isValidCombination() {
		return validPatterns.get(this.symbol).contains(this.color);
	}
}
