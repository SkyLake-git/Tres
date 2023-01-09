package com.tres.network.uno;

public class NetworkCard extends Card {


	public final int runtimeId;

	public NetworkCard(int runtimeId, Symbol symbol, Color color) {
		super(symbol, color);

		this.runtimeId = runtimeId;
	}
}
