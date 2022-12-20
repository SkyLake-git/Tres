package com.tres.client.ui.layout;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NarrowLayout extends Layout {

	protected int align;

	protected float padding;

	public NarrowLayout(Vector2 position, int width, int height, int align, float padding) {
		super(position, width, height);

		this.align = align;
		this.padding = padding;
	}

	@Override
	public void set(Actor actor, int index) {
		float width = 0;
		for (int i = index - 1; i >= 0; i--) {
			Actor e = this.actors.get(i);
			width += e.getWidth() + this.padding;
		}

		float x = this.position.x + width;
		float y = this.position.y;

		actor.setPosition(x, y, this.align);
	}
}
