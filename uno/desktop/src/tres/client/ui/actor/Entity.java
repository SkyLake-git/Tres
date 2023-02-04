package tres.client.ui.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract public class Entity extends Actor {
	protected Vector2 motion;

	protected double createdTimeMillis;

	public Entity(Vector2 position) {
		this.setX(position.x);
		this.setY(position.y);
		this.createdTimeMillis = System.currentTimeMillis();
		this.motion = new Vector2(0, 0);

		this.init();
	}

	abstract protected void init();

	public void updateMovement(float delta) {

		if (Math.abs(this.motion.x) < 1e-6) {
			this.motion.x = 0.0f;
		}

		if (Math.abs(this.motion.y) < 1e-6) {
			this.motion.y = 0.0f;
		}
		this.moveBy(this.motion.x * delta, this.motion.y * delta);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		this.updateMovement(delta);
	}

	public Vector2 getMotion() {
		return motion;
	}

	public void setMotion(Vector2 motion) {
		this.motion = motion.cpy();
	}
}
