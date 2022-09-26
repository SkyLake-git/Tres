package client.uno;

import client.ui.CardImage;
import client.ui.gui.MainPanel;
import network.uno.Card;
import network.uno.CardList;
import network.uno.Player;

import java.awt.*;

public class ClientPlayer extends Player {

	protected CardAssistant cardAssistant;

	protected CardList cards;

	protected String name;

	public ClientPlayer(int runtimeId, String name) {
		super(runtimeId);
		this.cardAssistant = new CardAssistant(this, new Point(0, MainPanel.HEIGHT - (MainPanel.HEIGHT / 10)));
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
		this.cardAssistant.getList().clear();
		this.cards.getAll().forEach((index, card) -> {
			CardImage visible = new CardImage(card.symbol, card.color);
			this.cardAssistant.add(visible);
		});
	}

	public int addCard(Card card) {
		int index = this.cards.add(card);
		this.refreshCardAssistant();
		return index;
	}

	public void removeCard(int index) {
		this.cards.remove(index);
		this.refreshCardAssistant();
	}

	@Override
	public CardList getCards() {
		return this.cards;
	}
}
