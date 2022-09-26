package client.ui;

import network.uno.Card;
import network.uno.CardValidator;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CardImageIds {

	public static final HashMap<Integer, Integer> MAP = new HashMap<Integer, Integer>();

	public static BufferedImage getImage(int index) {
		int id = MAP.get(index);

		return ImageManager.getImage(id);
	}

	public static BufferedImage getImage(Card.Symbol symbol, Card.Color color) {
		return getImage(getIndex(symbol, color));
	}

	public static int getIndex(Card.Symbol symbol, Card.Color color) {
		return symbol.i * color.ordinal();
	}

	public static String getImageName(Card.Symbol symbol, Card.Color color) {
		String second = "number";
		if (symbol.i > 9) {
			second = "symbol";
		}

		
		if (Card.Symbol.isWild(symbol)) {
			second = "wild";
		}
		return "./client/resources/card/" + second + "/card_" + color.toEnglishName() + "_" + symbol + ".png";
	}

	private static void register(Card.Symbol symbol, Card.Color color) {
		CardImageIds.MAP.put(getIndex(symbol, color), ImageManager.readImage(getImageName(symbol, color)));
	}

	static {
		for (Card.Symbol symbol : Card.Symbol.values()) {
			for (Card.Color color : Card.Color.values()) {
				if (CardValidator.validate(symbol, color)) {
					register(symbol, color);
				}
			}
		}
	}
}
