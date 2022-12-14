package tres.client.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import tres.client.ui.TextureUtils;
import tres.client.ui.WorldUtils;

public class BorderlessButtonActor extends Actor {

	public static class Button {
		public CharSequence text;
		public float width;
		public float height;
		public Color backgroundColor;
		public float confirmSeconds;

		public Button(CharSequence text, float width, float height, Color backgroundColor, float confirmSeconds) {
			this.text = text;
			this.width = width;
			this.height = height;
			this.backgroundColor = backgroundColor;
			this.confirmSeconds = confirmSeconds;
		}
	}

	protected Button button;

	protected BitmapFont font;

	protected Texture texture;

	protected boolean hit;

	protected boolean pressed;

	protected float hitTimes;

	protected float pressedTime;

	protected boolean disabled;

	public BorderlessButtonActor(Vector2 position, Button button) {
		setX(position.x - button.width / 2);
		setY(position.y - button.height / 2);

		this.button = button;
		this.font = new BitmapFont();
		this.texture = null;

		this.hitTimes = 0.0f;

		setWidth(button.width);
		setHeight(button.height);
		recreateTexture((int) button.width, (int) button.height, button.backgroundColor);
	}

	public void recreateTexture(int width, int height, Color colorDefault) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		this.recreateTexture(colorDefault, pixmap);

		pixmap.dispose();
	}

	public void recreateTexture(Color colorDefault, Pixmap basePixmap) {
		if (this.texture != null) this.texture.dispose();
		Color color = colorDefault.cpy();

		int width = basePixmap.getWidth();
		int height = basePixmap.getHeight();
		if (this.disabled) {
			color.add(0.15f, 0.15f, 0.15f, 0);
		}
		basePixmap.setColor(color);
		basePixmap.fillRectangle(1, 1, width - 2, height - 2);
		if (this.hit && !this.disabled) {
			basePixmap.setColor(0.0f, 0.5f, 0.5f, 1);
			basePixmap.drawRectangle(0, 0, width, height);
		}


		if (this.button.confirmSeconds > 0 && this.pressedTime > 0.0001f) {
			float percentage = (float) Math.min(1.0, this.pressedTime / this.button.confirmSeconds);
			if (percentage >= 1.0) {
			} else {
				basePixmap.setColor(1f, 1f, 0f, 0.4f);
				basePixmap.setColor(1f, 1f, 1f, 0.4f);
			}
			basePixmap.fillRectangle(0, 0, (int) (width * percentage), height);
		}
		this.texture = new Texture(basePixmap);
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();

		this.recreateTexture((int) getWidth(), (int) getHeight(), this.button.backgroundColor);
	}

	@Override
	protected void scaleChanged() {
		super.scaleChanged();

		this.recreateTexture((int) getWidth(), (int) getHeight(), this.button.backgroundColor);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.recreateTexture((int) getWidth(), (int) getHeight(), this.button.backgroundColor);
		batch.draw(this.texture, getX(), getY());
		Vector2 center = TextureUtils.getCenter(this);

		WorldUtils.drawAsScreenViewport(batch, this.getStage().getViewport(), () -> {
			this.font.draw(batch, this.button.text, center.x, center.y + 5, 0, Align.center, false);
		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		Vector2 pos = this.screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		boolean hit = this.hit(pos.x, pos.y, true) != null;

		this.pressed = false;
		if (this.button.confirmSeconds > 0) {
			if (this.hit && !this.disabled && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				this.pressedTime += delta;

				if (this.pressedTime > this.button.confirmSeconds) {
					this.pressed = true;
				}
			} else {
				this.pressedTime = 0.0f;
			}
		} else {
			this.pressed = this.hit && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !this.disabled;
		}


		if (hit && this.hit) {
			this.hitTimes += delta;
		} else {
			this.hitTimes = 0.0f;
		}

		this.hit = hit;

	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return super.hit(x, y, touchable);
	}

	public boolean isPressed() {
		return pressed;
	}

	@Override
	public boolean remove() {
		this.texture.dispose();
		return super.remove();
	}
}
