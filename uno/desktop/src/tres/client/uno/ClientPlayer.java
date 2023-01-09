package tres.client.uno;

import com.tres.network.packet.PlayerInfo;
import com.tres.network.uno.CardList;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.Player;

public class ClientPlayer extends Player {

	protected CardAssistant cardAssistant;

	protected CardList cards;

	protected PlayerInfo info;

	protected String displayName;

	public ClientPlayer(short runtimeId, PlayerInfo info) {
		super(runtimeId);
		this.cardAssistant = new CardAssistant(this);
		this.cards = new CardList(this);
		this.info = info;
		this.displayName = info.getUsername();
	}

	public CardAssistant getCardAssistant() {
		return cardAssistant;
	}

	public String getName() {
		return this.info.getUsername();
	}

	public String getDisplayName() {
		return this.displayName;
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
