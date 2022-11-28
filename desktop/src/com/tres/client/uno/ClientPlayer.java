package com.tres.client.uno;

import com.tres.network.uno.CardList;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.Player;

public class ClientPlayer extends Player {

	protected CardAssistant cardAssistant;

	protected CardList cards;

	protected String name;

	public ClientPlayer(int runtimeId, String name) {
		super(runtimeId);
		this.cardAssistant = new CardAssistant(this);
		this.cards = new CardList(this);
		this.name = name;
	}

	public CardAssistant getCardAssistant() {
		return cardAssistant;
	}

	public String getName() {
		return name;
	}

	public void refreshCardAssistant() {
	}

	public void addCard(NetworkCard card) {
		this.cards.add(card);
		this.refreshCardAssistant();
	}

	public void removeCard(int runtimeId) {
		this.cards.remove(runtimeId);
		this.refreshCardAssistant();
	}

	@Override
	public CardList getCards() {
		return this.cards;
	}
}
