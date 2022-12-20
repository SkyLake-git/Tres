package com.tres.client.ui.layout;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

abstract public class Layout {

	protected Vector2 position;
	protected int width;
	protected int height;

	protected ArrayList<Actor> actors;

	public Layout(Vector2 position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;

		this.actors = new ArrayList<>();
	}

	abstract public void set(Actor actor, int index);

	public void add(Actor actor) {
		this.actors.add(actor);

		int index = 0;

		for (Actor e : this.actors) {
			this.set(e, index);

			index++;
		}
	}

}
