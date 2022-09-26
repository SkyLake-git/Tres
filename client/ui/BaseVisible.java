package client.ui;

import math.Vector2;

import java.awt.*;

abstract public class BaseVisible implements Visible {

	protected boolean isColliding = false;

	public Rectangle rect;

	BaseVisible() {
		this.rect = new Rectangle();
	}


	public Rectangle getRect() {
		return (Rectangle) this.rect.clone();
	}

	@Override
	public boolean collide(Vector2 pos) {
		return rect.x <= pos.x && pos.x <= rect.x + rect.width
				&& rect.y <= pos.y && pos.y <= rect.y + rect.height;
	}
}
