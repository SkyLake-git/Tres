package client.ui;

import math.Vector2;

import java.awt.*;

public interface Visible {

	void draw(Graphics g);

	void update();

	boolean collide(Vector2 pos);
}
