package tres.client.uno;

import com.tres.network.uno.CardList;
import com.tres.network.uno.NetworkCard;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PlayerCardActions {

	protected ClientPlayer player;

	protected ArrayList<Consumer<NetworkCard>> addedListeners;

	protected ArrayList<Consumer<NetworkCard>> removedListeners;

	public PlayerCardActions(ClientPlayer player) {
		this.player = player;
		this.addedListeners = new ArrayList<>();
		this.removedListeners = new ArrayList<>();
	}

	public ClientPlayer getPlayer() {
		return player;
	}

	public CardList getCards() {
		return this.player.getCardList();
	}

	public void addCardAddedListener(Consumer<NetworkCard> consumer) {
		this.addedListeners.add(consumer);
	}

	public void addCardRemovedListener(Consumer<NetworkCard> consumer) {
		this.removedListeners.add(consumer);
	}

	protected void emitAdd(NetworkCard card) {
		for (Consumer<NetworkCard> listener : this.addedListeners) {
			listener.accept(card);
		}
	}

	protected void emitRemove(NetworkCard card) {
		for (Consumer<NetworkCard> listener : this.removedListeners) {
			listener.accept(card);
		}
	}

	public void add(NetworkCard... cards) {
		for (NetworkCard card : cards) {
			this.getCards().add(card);

			this.emitAdd(card);
		}
	}

	public void remove(int... runtimeIds) {
		for (int id : runtimeIds) {
			if (this.getCards().getAll().containsKey(id)) {
				this.emitRemove(this.getCards().get(id));
			}

			this.getCards().remove(id);
		}
	}
}
