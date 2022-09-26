package client.ui.gui.sequence;

import client.ui.BasicButton;
import client.ui.Button;
import client.ui.VisibleManager;
import client.ui.gui.DrawUtil;
import client.ui.gui.MainPanel;

import java.awt.*;

public class MainSequence extends BaseSequence {

	protected ISequence child;

	public static final int SEQ_EXIT = 0;
	public static final int SEQ_LOOP = 1;

	protected int circleSize;
	protected boolean reverse;

	protected VisibleManager visibleManager;

	protected Button exitButton;

	protected Button connectButton;

	protected MainPanel panel;

	public MainSequence(MainPanel panel) {
		super(panel);
		this.panel = panel;
		this.circleSize = 10;
		this.reverse = false;
		this.visibleManager = new VisibleManager();
		this.exitButton = new BasicButton(new Point(MainPanel.WIDTH / 2, 200), 140, 70, Color.red, "Exit");
		this.connectButton = new BasicButton(new Point(MainPanel.WIDTH / 2, MainPanel.HEIGHT - 100), 120, 60, Color.red, "Connect");

		this.visibleManager.add(this.exitButton);
		this.visibleManager.add(this.connectButton);
	}


	@Override
	public int update(ISequence parent) {
		int result = MainPanel.UPDATE_SUCCESS;

		this.visibleManager.update();

		int scale = 2;
		if (this.reverse) {
			scale *= -1;
		}
		this.circleSize += scale;
		if (this.circleSize > MainPanel.WIDTH) {
			this.reverse = true;
		}
		if (this.circleSize < 0) {
			this.reverse = false;
		}

		if (this.exitButton.isClicked()) {
			this.panel.setSequence(new ExitSequence(this.panel));
		}

		if (this.connectButton.isClicked()) {
			this.panel.setSequence(new ConnectSequence(this.panel));
		}

		return result;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.orange);

		this.visibleManager.draw(g);

		DrawUtil.drawOvalCentered(g, MainPanel.WIDTH / 2, MainPanel.HEIGHT / 2, this.circleSize, this.circleSize);

	}

	@Override
	public void close() {
	}

	public void releaseChild() {
		if (this.child != null) {
			this.child.close();
			this.child = null;
		}
	}
}
