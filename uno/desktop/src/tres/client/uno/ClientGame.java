package tres.client.uno;

import com.tres.network.packet.protocol.GameLevelPacket;
import com.tres.network.uno.Game;
import com.tres.network.uno.Player;
import com.tres.network.uno.container.NetworkCardContainer;
import com.tres.network.uno.rule.UnoGameRule;

import java.util.ArrayList;

public class ClientGame extends Game {

	protected int id;

	protected ArrayList<Player> players;

	protected ArrayList<Player> inGamePlayers;

	protected NetworkCardContainer stack;

	protected UnoGameRule rule;

	public ClientGame(int id, UnoGameRule rule) {
		this.id = id;
		this.players = new ArrayList<>();
		this.inGamePlayers = new ArrayList<>();
		this.stack = new NetworkCardContainer();
		this.rule = rule;
	}

	public UnoGameRule getRule() {
		return rule;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Player> getInGamePlayers() {
		return inGamePlayers;
	}

	public NetworkCardContainer getStack() {
		return stack;
	}

	public void onPlay(short player) {

	}

	public int getId() {
		return id;
	}

	public void onSync(GameLevelPacket packet) {
		this.id = packet.gameId;
		this.deckCount = packet.deckCards;
		this.playerCount = packet.players.size();
		// sync する
	}
}
