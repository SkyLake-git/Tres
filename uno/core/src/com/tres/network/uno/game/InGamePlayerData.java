package com.tres.network.uno.game;

public interface InGamePlayerData<P, G, T> {

	P getPlayer();

	G getGame();

	T getTurnData();

	void createTurnData();

	void destroyTurnData();

	boolean isMyTurn();

	void accusateUno();
}
