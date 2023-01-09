package tres.client.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.tres.network.uno.Card;
import com.tres.network.uno.CardValidator;

import java.util.HashMap;

public class CardTextures {

	public static final HashMap<Integer, Texture> MAP = new HashMap<>();


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
		return color.toEnglishName() + "_" + symbol + ".png";
	}

	private static void register(Card.Symbol symbol, Card.Color color) {
		CardTextures.MAP.put(getIndex(symbol, color), new Texture(Gdx.files.internal(getImageName(symbol, color))));
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
