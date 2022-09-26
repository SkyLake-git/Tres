package client.ui.gui.sequence;

import client.ui.gui.DrawUtil;
import client.ui.gui.MainPanel;

import java.awt.*;

public class ExitSequence extends BaseSequence {

	protected int tick;

	protected double lastPer;

	public ExitSequence(MainPanel panel) {
		super(panel);
		this.tick = 0;
		this.lastPer = 0.0;
	}

	@Override
	public int update(ISequence parent) {
		this.tick++;
		if (this.tick > (2 * 60)) {
			return MainSequence.SEQ_EXIT;
		} else {
			return MainSequence.SEQ_LOOP;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 120));
		g.setColor(Color.black);
		int x = MainPanel.WIDTH / 2;
		int y = MainPanel.HEIGHT / 2;
		DrawUtil.fillOvalCentered(g, x, y, 2000, 2000);

		this.lastPer = ((double) this.tick) / (2 * 60);
		double level = ((2000 - (2000 * this.lastPer)));
		g.setColor(Color.gray);
		DrawUtil.fillOvalCentered(g, x, y, (int) level, (int) level);

		g.setColor(Color.darkGray);
		if (this.tick == ((2 * 60))) {
			DrawUtil.drawStringCentered(g, x, y, "Closing Client...");
		} else {
			DrawUtil.drawStringCentered(g, x, y, "See you next time");
		}

	}

	@Override
	public void close() {
		this.tick = 0;
	}
}
