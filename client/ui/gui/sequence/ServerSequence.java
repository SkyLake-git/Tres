package client.ui.gui.sequence;

import client.ui.gui.MainPanel;

import java.awt.*;

public class ServerSequence extends BaseSequence {

	public ServerSequence(MainPanel panel) {
		super(panel);
	}

	@Override
	public int update(ISequence parent) {

		return MainPanel.UPDATE_SUCCESS;
	}

	@Override
	public void render(Graphics g) {

	}

	@Override
	public void close() {

	}
}
