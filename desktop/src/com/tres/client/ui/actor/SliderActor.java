package com.tres.client.ui.actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

public class SliderActor extends Slider {

	protected BitmapFont font;

	protected String format;

	public SliderActor(Vector2 position, float width, float height, float min, float max, float stepSize, boolean vertical, BitmapFont font, String format) {
		super(min, max, stepSize, vertical, new SliderStyle());

		setPosition(position.x - width / 2, position.y - width / 2);

		setSize(width, height);

		this.font = font;
		this.format = format;

		this.refreshStyle();
	}

	private void refreshStyle() {
		int width = (int) getWidth();
		int height = (int) getHeight();

		Pixmap backgroundPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		backgroundPixmap.setColor(0, 0, 0, 1f);
		backgroundPixmap.fill();
		NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture(backgroundPixmap)));

		Pixmap backgroundDownPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		backgroundDownPixmap.setColor(0.0f, 0.4f, 0.2f, 0.5f);
		backgroundDownPixmap.fill();
		NinePatchDrawable backgroundDown = new NinePatchDrawable(new NinePatch(new Texture(backgroundDownPixmap)));

		Pixmap knobPixmap = new Pixmap(10, height + 6, Pixmap.Format.RGBA8888);
		knobPixmap.setColor(1f, 1f, 1f, 1f);
		knobPixmap.fill();
		NinePatchDrawable knob = new NinePatchDrawable(new NinePatch(new Texture(knobPixmap)));

		int downLineWidth = 6;
		Pixmap knobDownPixmap = new Pixmap(10 + downLineWidth, height + 6 + downLineWidth, Pixmap.Format.RGBA8888);
		knobDownPixmap.setColor(0.15f, 0.15f, 0.15f, 1f);
		knobDownPixmap.fill();
		knobDownPixmap.setColor(0.9f, 0.9f, 0.9f, 1f);
		knobDownPixmap.fillRectangle(downLineWidth / 2, downLineWidth / 2, 10, height + 6);
		NinePatchDrawable knobDown = new NinePatchDrawable(new NinePatch(new Texture(knobDownPixmap)));

		SliderStyle style = new SliderStyle(background, knob);
		style.knobBeforeOver = backgroundDown;
		style.knobBeforeDown = backgroundDown;
		style.knobDown = knobDown;

		setStyle(style);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		this.font.draw(batch, String.format(this.format, this.getVisualValue()), getX() - 5, getY() + (getHeight() / 2) + 5, 0, Align.top, false);
	}
}
