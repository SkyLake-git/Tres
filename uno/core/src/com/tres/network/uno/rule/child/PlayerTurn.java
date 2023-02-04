package com.tres.network.uno.rule.child;

public class PlayerTurn {

	private double timeLimit;

	public static PlayerTurn create(double timeLimit) {
		PlayerTurn s = new PlayerTurn();
		s.timeLimit = timeLimit;

		return s;
	}

	private PlayerTurn() {

	}

	public double timeLimit() {
		return timeLimit;
	}
}
