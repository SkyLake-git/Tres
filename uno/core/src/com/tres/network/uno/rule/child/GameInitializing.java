package com.tres.network.uno.rule.child;

public class GameInitializing {

	private int cards;

	private GameInitializing() {

	}

	public static GameInitializing create(int cards) {
		GameInitializing s = new GameInitializing();
		s.cards = cards;

		return s;
	}

	public int cards() {
		return cards;
	}
}
