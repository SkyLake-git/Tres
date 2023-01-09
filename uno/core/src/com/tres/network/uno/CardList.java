package com.tres.network.uno;

import java.util.HashMap;

public class CardList extends CardInfo {

	protected HashMap<Integer, NetworkCard> cards;

	public CardList(Player player) {
		super(player);

		this.cards = new HashMap<>();
	}

	protected void update() {
		this.count = this.cards.size();
	}

	protected void set(int runtimeId, NetworkCard card) {
		this.cards.put(runtimeId, card);
		this.update();
	}

	public void add(NetworkCard card) {
		this.set(card.runtimeId, card);
	}

	public void remove(int runtimeId) {
		this.cards.remove(runtimeId);
		this.update();
	}

	public HashMap<Integer, NetworkCard> getAll() {
		return this.cards;
	}

	public void clear() {
		this.cards.clear();
		this.update();
	}

	public Card get(int runtimeId) {
		return this.cards.get(runtimeId);
	}
}
