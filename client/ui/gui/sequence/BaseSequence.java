package client.ui.gui.sequence;

import client.ui.gui.MainPanel;

abstract public class BaseSequence implements ISequence {

	protected MainPanel panel;

	public BaseSequence(MainPanel panel) {
		this.panel = panel;
	}
}
