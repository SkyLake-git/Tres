package com.tres.network.uno.rule.child;

public class PlayerTurn {

	private double timeLimit;

	private PlayerTurn() {

	}

	public static PlayerTurn create(double timeLimit) {
		PlayerTurn s = new PlayerTurn();
		s.timeLimit = timeLimit;

		return s;
	}

	public double timeLimit() {
		return timeLimit;
	}
}
