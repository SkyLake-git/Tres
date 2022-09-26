package client.uno;

import client.ui.BaseVisible;
import client.ui.VisibleManager;
import client.ui.gui.MainPanel;
import client.ui.util.HoldUtil;

import java.awt.*;
import java.util.ArrayList;

public class CardAssistant extends VisibleManager {

	protected ClientPlayer player;

	protected Point position;

	protected ArrayList<BaseVisible> list;

	public CardAssistant(ClientPlayer player, Point position) {
		this.player = player;
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	@Override
	public void update() {
		ArrayList<Point> offsets = HoldUtil.getOffsets(this.list, this.position, MainPanel.WIDTH, -10);

		for (int i = 0; i < offsets.size(); i++) {
			Point offset = offsets.get(i);
			BaseVisible visible = this.list.get(i);
			visible.rect.x = offset.x;
			visible.rect.y = offset.y;
		}

		for (BaseVisible visible : this.list) {
			visible.update();
		}
	}
}
