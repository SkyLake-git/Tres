package com.tres.network.uno;

public class CardInfo {

	protected Player player;

	protected int count;

	CardInfo(Player player) {
		this.player = player;
		this.count = 0;
	}

	public Player getPlayer() {
		return player;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
