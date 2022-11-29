package com.tres.client;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

public class AbsoluteDrawer {

	protected SpriteBatch batch;

	protected ArrayList<Actor> actors;

	protected Group root;

	public AbsoluteDrawer() {
		this.batch = new SpriteBatch();
		this.actors = new ArrayList<>();

		this.root = new Group();
	}


	public void addActor(Actor actor) {
		this.root.addActor(actor);
	}

	public void refreshBatch() {
		this.batch.dispose();
		this.batch = new SpriteBatch();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void act(float delta) {
		this.root.act(delta);
	}

	public void draw() {
		this.batch.begin();
		this.root.draw(this.batch, 1);
		this.batch.end();
	}
}
