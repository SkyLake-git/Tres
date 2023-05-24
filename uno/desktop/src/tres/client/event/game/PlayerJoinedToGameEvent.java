package tres.client.event.game;

public class PlayerJoinedToGameEvent extends GameEvent {

	protected short playerRuntimeId;

	public PlayerJoinedToGameEvent(int gameId, short playerRuntimeId) {
		super(gameId);

		this.playerRuntimeId = playerRuntimeId;
	}

	public short getPlayerRuntimeId() {
		return playerRuntimeId;
	}
}
