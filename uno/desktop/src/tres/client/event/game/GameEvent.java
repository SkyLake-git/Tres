package tres.client.event.game;

import com.tres.event.BaseEvent;

public class GameEvent extends BaseEvent {

	protected int gameId;

	public GameEvent(int gameId) {
		super();

		this.gameId = gameId;
	}

	public int getGameId() {
		return gameId;
	}
}
