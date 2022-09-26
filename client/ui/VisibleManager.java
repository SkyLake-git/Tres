package client.ui;

import java.awt.*;
import java.util.ArrayList;

public class VisibleManager {

	protected ArrayList<Visible> list;

	public VisibleManager() {
		this.list = new ArrayList<Visible>();
	}

	public void add(Visible visible) {
		this.list.add(visible);
	}

	public ArrayList<Visible> getList() {
		return list;
	}

	public void update() {
		for (Visible visible : this.list) {
			visible.update();
		}
	}

	public void draw(Graphics g) {
		Color prevColor = g.getColor();
		for (Visible visible : this.list) {
			visible.draw(g);
			g.setColor(prevColor);
		}

	}
}
