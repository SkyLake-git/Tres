package com.tres.network.uno.rule.child;

public class GameInitializing {

	private int cards;

	public static GameInitializing create(int cards) {
		GameInitializing s = new GameInitializing();
		s.cards = cards;

		return s;
	}

	private GameInitializing() {

	}

	public int cards() {
		return cards;
	}
}
