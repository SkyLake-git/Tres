package com.tres.network.uno;

abstract public class Game {

	protected int playerCount;

	protected short turnPlayer;

	protected int deckCount;

	protected int stackCount;

	public Game() {
		this.playerCount = 0;
		this.deckCount = 0;
		this.stackCount = 0;
		this.turnPlayer = 0;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public short getTurnPlayer() {
		return turnPlayer;
	}

	public int getDeckCount() {
		return deckCount;
	}

	public int getStackCount() {
		return stackCount;
	}
}
