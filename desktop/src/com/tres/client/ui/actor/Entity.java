package com.tres.client.ui.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {

	protected Vector2 position;
	protected Vector2 motion;

	public Entity(Vector2 position) {
		this.position = position;
		this.motion = new Vector2(0, 0);
	}

	public void updateMovement() {

		if (this.motion.x < 1e-6) {
			this.motion.x = 0.0f;
		}

		if (this.motion.y < 1e-6) {
			this.motion.y = 0.0f;
		}

		this.position.add(this.motion);

		this.setX(this.position.x);
		this.setY(this.position.y);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		this.updateMovement();
	}

	public Vector2 getMotion() {
		return motion;
	}

	public void setMotion(Vector2 motion) {
		this.motion = motion;
	}
}
