package client.ui.gui.effect;

import math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Polyline {

	protected ArrayList<Vector2> vec;

	public Polyline() {
		this.vec = new ArrayList<Vector2>();
	}

	public void addPoint(Vector2 vec) {
		this.vec.add(vec);
	}

	public void each(Consumer<? super Vector2> consumer) {
		this.vec.forEach(consumer);
	}

	public void loopLine(Dimension dimension) {
		this.vec.forEach(vec -> {
			if (vec.x > dimension.width) {
				vec.x -= dimension.width;
			}

			if (vec.x < 0) {
				vec.x += dimension.width;
			}

			if (vec.y > dimension.height) {
				vec.y -= dimension.height;
			}

			if (vec.y < 0) {
				vec.y += dimension.height;
			}
		});
	}


	public void draw(Graphics g) {
		int[] x = new int[this.vec.size()];
		int[] y = new int[this.vec.size()];
		int current = 0;
		for (Vector2 vec : this.vec) {
			x[current] = (int) vec.x;
			y[current] = (int) vec.y;
			current++;
		}

		g.drawPolyline(x, y, this.vec.size());
	}
}
