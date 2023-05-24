package tres.client.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.tres.network.uno.Card;

import java.util.HashMap;

public class CardTextures {

	public static final HashMap<Integer, Texture> MAP = new HashMap<>();

	public static final int WIDTH = 161;

	public static final int HEIGHT = 266;

	public static Texture BACK;

	public static int getIndex(Card.Symbol symbol, Card.Color color) {
		return symbol.hashCode() + color.hashCode();
	}

	public static String getImageName(Card.Symbol symbol, Card.Color color) {
		String second = "number";
		if (symbol.i > 9) {
			second = "symbol";
		}


		if (symbol.isWild()) {
			second = "wild";
		}
		return second + "/" + "card_" + color.toEnglishName() + "_" + symbol + ".png";
	}

	private static void register(Card.Symbol symbol, Card.Color color) {
		int ind = getIndex(symbol, color);
		if (MAP.containsKey(ind)) {
			throw new RuntimeException("contains");
		}

		CardTextures.MAP.put(ind, new Texture(Gdx.files.internal("card/" + getImageName(symbol, color))));
	}

	public static void init() {
		if (MAP.size() > 0) {
			return;
		}

		BACK = new Texture(Gdx.files.internal("card/card_back.png"));

		for (Card.Symbol symbol : Card.Symbol.values()) {
			for (Card.Color color : Card.Color.values()) {
				if ((new Card(symbol, color)).isValidCombination()) {
					register(symbol, color);
				}
			}
		}
	}
}
