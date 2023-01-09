package tres.client.ui.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class TextActor extends Actor {

	public static TextActor annotation(Actor base, CharSequence text, BitmapFont font) {
		TextActor actor = new TextActor(text, font);
		actor.setPosition(base.getX(), base.getY() + 2, Align.topLeft);
		return actor;
	}

	protected CharSequence text;

	protected BitmapFont font;
	protected float duration;

	public TextActor(CharSequence text, BitmapFont font) {
		this.text = text;
		this.font = font;
		this.duration = -1f;
	}

	public TextActor(CharSequence text, BitmapFont font, float duration) {
		this.text = text;
		this.font = font;
		this.duration = duration;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		this.font.draw(batch, this.text, getX(), getY());
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (this.duration > 0f) {
			this.duration -= delta;

			if (this.duration <= 0f) {
				this.remove();
			}
		}
	}

	public CharSequence getText() {
		return text;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setText(CharSequence text) {
		this.text = text;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}
}
