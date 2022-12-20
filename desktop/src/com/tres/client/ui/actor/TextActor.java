package com.tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor {

	protected CharSequence text;

	protected BitmapFont font;
	protected Color color;

	public TextActor(CharSequence text, BitmapFont font, Color color) {
		this.text = text;
		this.font = font;
		this.color = color;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		this.font.setColor(this.color);
		this.font.draw(batch, this.text, getX(), getY());
	}
}
