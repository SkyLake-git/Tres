package tres.client.uno;

import com.tres.network.packet.PlayerInfo;
import com.tres.network.uno.CardInfo;
import com.tres.network.uno.CardList;
import com.tres.network.uno.Player;

public class ClientPlayer extends Player {

	protected CardList cards;

	protected PlayerCardActions cardActions;

	protected PlayerInfo info;

	protected String displayName;

	public ClientPlayer(short runtimeId, PlayerInfo info) {
		super(runtimeId);
		this.cards = new CardList();
		this.cardActions = new PlayerCardActions(this);
		this.info = info;
		this.displayName = info.getUsername();
	}

	public PlayerCardActions getCardActions() {
		return cardActions;
	}

	public String getName() {
		return this.info.getUsername();
	}

	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public CardInfo getCards() {
		return (CardInfo) this.cards;
	}

	CardList getCardList() {
		return (CardList) this.cards;
	}
}
