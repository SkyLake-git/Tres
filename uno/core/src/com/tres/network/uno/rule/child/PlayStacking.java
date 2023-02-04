package com.tres.network.uno.rule.child;

public class PlayStacking {

	private boolean enabled = true;

	private boolean onlySymbolMatch;

	private int maxSize;

	private boolean stackDraws;

	private boolean stackWilds;

	public static PlayStacking official() {
		return create(false, Integer.MAX_VALUE, false, false);
	}

	public static PlayStacking disabled() {
		PlayStacking s = new PlayStacking();
		s.enabled = false;
		return s;
	}

	public static PlayStacking create(boolean onlySymbolMatch, int maxSize, boolean stackDraws, boolean stackWilds) {
		PlayStacking s = new PlayStacking();
		s.onlySymbolMatch = onlySymbolMatch;
		s.maxSize = maxSize;
		s.stackDraws = stackDraws;
		s.stackWilds = stackWilds;

		return s;
	}

	private PlayStacking() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int maxSize() {
		return maxSize;
	}

	public boolean onlySymbolMatch() {
		return onlySymbolMatch;
	}

	public boolean stackDraws() {
		return stackDraws;
	}

	public boolean stackWilds() {
		return stackWilds;
	}
}
