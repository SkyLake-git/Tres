package tres.client.event.game;

public class PlayerLeftGameEvent extends GameEvent {
	protected short playerRuntimeId;

	public PlayerLeftGameEvent(int gameId, short playerRuntimeId) {
		super(gameId);

		this.playerRuntimeId = playerRuntimeId;
	}

	public short getPlayerRuntimeId() {
		return playerRuntimeId;
	}
}
