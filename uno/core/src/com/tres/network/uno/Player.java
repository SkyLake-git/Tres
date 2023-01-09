package com.tres.network.uno;

abstract public class Player {

	private static short CURRENT_ID = 0;

	public static short nextRuntimeId() {
		return CURRENT_ID++;
	}

	protected short runtimeId;

	protected CardInfo cards;

	public Player(short runtimeId) {
		this.runtimeId = runtimeId;
		this.cards = new CardInfo(this);
	}

	public CardInfo getCards() {
		return cards;
	}

	public short getId() {
		return this.runtimeId;
	}
}
