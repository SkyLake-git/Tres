package com.tres.network.uno.game;

import java.util.Collection;

public interface PlayerTurnData<ID> {

	int PHASE_NONE = 0;

	int PHASE_DREW = 1;

	int PHASE_UNO = 2;

	void tick();

	int getTick();

	boolean isUnoAccused();

	ID getInGameData();

	void onUnoAccused(short playerBy);

	boolean isShoutedUno();

	boolean haveToShoutUno();

	int getPhase();

	void setPhase(int phase);

	void drawCard();

	void shoutUno();

	void skipTurn();

	void playCard(Collection<Integer> cardRuntimeIds);
}
