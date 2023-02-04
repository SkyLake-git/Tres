package com.tres.network.uno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class CardList extends CardInfo {

	protected HashMap<Integer, NetworkCard> cards;

	protected ArrayList<Consumer<HashMap<Integer, NetworkCard>>> changeListeners;

	protected boolean dirty;

	public CardList() {
		this.cards = new HashMap<>();
		this.changeListeners = new ArrayList<>();
		this.dirty = false;
	}

	public void addListener(Consumer<HashMap<Integer, NetworkCard>> listener) {
		this.changeListeners.add(listener);
	}

	public void removeListener(Consumer<HashMap<Integer, NetworkCard>> listener) {
		this.changeListeners.remove(listener);
	}

	protected void update() {
		this.count = this.cards.size();
		this.dirty = true;
	}

	public boolean doCleaning() {
		boolean cleaned = this.dirty;

		if (this.dirty) {
			this.onChange();
		}

		this.dirty = false;

		return cleaned;
	}

	protected void onChange() {
		for (Consumer<HashMap<Integer, NetworkCard>> listener : this.changeListeners) {
			listener.accept(this.cards);
		}
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

	public NetworkCard get(int runtimeId) {
		return this.cards.get(runtimeId);
	}
}
