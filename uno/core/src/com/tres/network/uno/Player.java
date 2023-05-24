package com.tres.network.uno;

abstract public class Player {

	private static short CURRENT_ID = 0;

	protected short runtimeId;

	public Player(short runtimeId) {
		this.runtimeId = runtimeId;
	}

	public static short nextRuntimeId() {
		return CURRENT_ID++;
	}

	public short getId() {
		return this.runtimeId;
	}
}
