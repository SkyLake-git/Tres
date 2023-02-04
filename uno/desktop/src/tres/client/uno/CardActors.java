package tres.client.uno;

import com.tres.network.uno.Card;
import tres.client.ui.actor.CardActor;

import java.util.HashMap;

public class CardActors {

	protected HashMap<Integer, CardActor> actors;

	protected HashMap<Card, Integer> id;

	public CardActors() {
		this.actors = new HashMap<>();
		this.id = new HashMap<>();
	}

	public void add(int runtimeId, CardActor actor) {
		this.actors.put(runtimeId, actor);
		this.id.put(actor.getCard(), runtimeId);
	}

	public void remove(CardActor actor) {
		Integer runtimeId = this.id.get(actor.getCard());
		if (runtimeId != null) {
			this.actors.remove(runtimeId);
			this.id.remove(actor.getCard());
		}
	}

	public HashMap<Integer, CardActor> getActorMap() {
		return actors;
	}

	public HashMap<Card, Integer> getIdMap() {
		return this.id;
	}
}
