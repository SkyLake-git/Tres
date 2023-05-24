package tres.client.ui.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract public class Entity extends Actor implements DynamicTextureActor {
	protected Vector2 motion;

	protected double createdTimeMillis;

	protected boolean isRequestedRedrawTexture;

	public Entity() {
		this.createdTimeMillis = System.currentTimeMillis();
		this.motion = new Vector2(0, 0);
		this.isRequestedRedrawTexture = false;

		this.init();
	}

	@Override
	public void requestRedrawTexture() {
		this.isRequestedRedrawTexture = true;
	}

	@Override
	public boolean isRequestedRedrawTexture() {
		return this.isRequestedRedrawTexture;
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
