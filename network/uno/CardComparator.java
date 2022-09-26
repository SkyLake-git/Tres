package network.uno;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

	@Override
	public int compare(Card a, Card b) {
		int acp = a.color.ordinal();
		int bcp = b.color.ordinal();

		if (acp < bcp) {
			return -1;
		} else if (acp > bcp) {
			return 1;
		}

		int as = a.symbol.i;
		int bs = b.symbol.i;

		if (as < bs) {
			return -1;
		} else if (as > bs) {
			return 1;
		}

		return 0;
	}
}
