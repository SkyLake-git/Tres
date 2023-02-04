package tres.sequence.ingame;

import com.badlogic.gdx.math.Vector2;
import tres.client.ui.actor.CardActor;

import java.util.Collection;

public class CardRenderer {

	public CardRenderer() {
	}

	public void update(Vector2 position, Collection<CardActor> cards, int width, int height, int cardWidth) {
		int cardCount = cards.size();
		int widthWill = cardWidth * cardCount;
		float widthOver = widthWill / width;

		int doCardWidth;
		if (widthOver > 1.0) {
			doCardWidth = (int) (cardWidth / widthOver);
		} else {
			doCardWidth = cardWidth;
		}

		int start = (int) (position.x - (doCardWidth * cardCount / 2));

		int i = 0;
		for (CardActor actor : cards) {
			actor.setPosition(start + (doCardWidth * i), position.y);
			i++;
		}
	}
}
