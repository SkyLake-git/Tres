package network.uno;

abstract public class Player {

	private static int CURRENT_ID = 0;

	public static int nextRuntimeId() {
		return CURRENT_ID++;
	}

	protected int runtimeId;

	protected CardInfo cards;

	public Player(int runtimeId) {
		this.runtimeId = runtimeId;
		this.cards = new CardInfo(this);
	}

	public CardInfo getCards() {
		return cards;
	}

	public int getId() {
		return this.runtimeId;
	}
}
