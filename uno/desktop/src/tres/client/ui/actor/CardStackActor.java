package tres.client.ui.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class CardStackActor extends Actor {

	protected LinkedHashMap<Integer, StackedCardInfo> cards;

	protected Vector2 diffRandomRange;

	protected float rotRandomRange;

	protected Integer lastPutId;

	public CardStackActor() {
		this.cards = new LinkedHashMap<>();
		this.diffRandomRange = new Vector2(25, 25);
		this.rotRandomRange = 360;
		this.lastPutId = null;
	}

	public void addToTop(int id, CardActor actor) {
		actor.setSize(actor.getWidth() * 0.5f, actor.getHeight() * 0.5f);
		this.cards.put(id, new StackedCardInfo(
				new Vector2((float) ((Math.random() - 0.5) * this.diffRandomRange.x), (float) ((Math.random() - 0.5) * this.diffRandomRange.y)),
				(float) (Math.random() * this.rotRandomRange),
				actor
		));

		this.lastPutId = id;
	}

	public StackedCardInfo getTopStack() {
		if (this.lastPutId == null) {
			return null;
		}

		return this.cards.get(this.lastPutId);
	}

	public LinkedHashMap<Integer, StackedCardInfo> getCards() {
		return cards;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		float y = this.getY();

		if (this.cards.size() > 0) {
			Collection<Integer> sorted = this.cards.keySet().stream().sorted().collect(Collectors.toList());

			for (int i : sorted) {
				StackedCardInfo card = this.cards.get(i);
				card.actor.setPosition(this.getX() + card.diff.x, y + card.diff.y);
				card.actor.setRotation(card.getRot());
				card.actor.draw(batch, parentAlpha);
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (StackedCardInfo card : this.cards.values()) {
			card.actor.act(delta);
		}
	}

	public static class StackedCardInfo {

		public CardActor actor;

		protected Vector2 diff;

		protected float rot;

		public StackedCardInfo(Vector2 diff, float rot, CardActor actor) {
			this.diff = diff;
			this.rot = rot;
			this.actor = actor;
		}

		public CardActor getActor() {
			return actor;
		}

		public Vector2 getDiff() {
			return diff;
		}

		public float getRot() {
			return rot;
		}

		public void setRot(float rot) {
			this.rot = rot;
		}
	}
}
