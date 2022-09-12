package network.uno;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class CardList extends CardInfo {

	protected HashMap<Integer, Card> cards;

	CardList(Player player) {
		super(player);

		this.cards = new HashMap<Integer, Card>();
	}

	protected void update() {
		this.count = this.cards.size();
	}

	void set(int index, Card card) {
		this.cards.put(index, card);
		this.update();
	}

	void add(Card card) {
		Optional<Integer> maxIndex = this.cards.keySet().stream().max(Comparator.naturalOrder());
		int index = 0;
		if (maxIndex.isPresent()) {
			index = maxIndex.get() + 1;
		}

		this.set(index, card);
	}

	void remove(int index) {
		this.cards.remove(index);
		this.update();
	}

	void clear() {
		this.cards.clear();
		this.update();
	}

	Card fetch() {
		Optional<Integer> maxIndex = this.cards.keySet().stream().max(Comparator.naturalOrder());
		if (maxIndex.isPresent()) {
			int index = maxIndex.get();
			return this.get(index);
		}

		return null;
	}

	Card get(int index) {
		return this.cards.get(index);
	}
}
