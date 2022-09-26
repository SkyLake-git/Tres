package network.uno;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class CardList extends CardInfo {

	protected HashMap<Integer, Card> cards;

	public CardList(Player player) {
		super(player);

		this.cards = new HashMap<Integer, Card>();
	}

	protected void update() {
		this.count = this.cards.size();
	}

	public void set(int index, Card card) {
		this.cards.put(index, card);
		this.update();
	}

	public int add(Card card) {
		Optional<Integer> maxIndex = this.cards.keySet().stream().max(Comparator.naturalOrder());
		int index = 0;
		if (maxIndex.isPresent()) {
			index = maxIndex.get() + 1;
		}

		this.set(index, card);

		return index;
	}

	public void remove(int index) {
		this.cards.remove(index);
		this.update();
	}

	public HashMap<Integer, Card> getAll() {
		return this.cards;
	}

	public void clear() {
		this.cards.clear();
		this.update();
	}

	public Card fetch() {
		Optional<Integer> maxIndex = this.cards.keySet().stream().max(Comparator.naturalOrder());
		if (maxIndex.isPresent()) {
			int index = maxIndex.get();
			return this.get(index);
		}

		return null;
	}

	public Card get(int index) {
		return this.cards.get(index);
	}
}
