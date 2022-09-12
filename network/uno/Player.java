package network.uno;

abstract public class Player {

	protected int runtimeId;

	public Player(int runtimeId) {
		this.runtimeId = runtimeId;
	}

	int getId() {
		return this.runtimeId;
	}
}
