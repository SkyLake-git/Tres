package tres.client.uno;

import com.tres.network.packet.PlayerInfo;
import com.tres.network.uno.Player;
import com.tres.network.uno.container.NetworkCardContainer;

public class ViewPlayer extends Player {
	protected NetworkCardContainer cards;

	protected PlayerInfo info;

	protected String displayName;

	public ViewPlayer(short runtimeId, PlayerInfo info) {
		super(runtimeId);
		this.cards = new NetworkCardContainer();
		this.info = info;
		this.displayName = info.getUsername();
	}

	public String getName() {
		return this.info.getUsername();
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public NetworkCardContainer getCards() {
		return cards;
	}

	public PlayerInfo getInfo() {
		return info;
	}
}
