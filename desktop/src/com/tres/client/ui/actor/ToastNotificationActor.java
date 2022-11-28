package com.tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class ToastNotificationActor extends Entity {

	protected Texture texture;

	public ToastNotificationActor(Vector2 position, float width, float height, Color color) {
		super(position);

		this.setMotion(new Vector2(0, -2));

		setX(position.x);
		setY(position.y);

		setWidth(width);
		setHeight(height);

		recreateTexture((int) width, (int) height, color);
	}

	private void recreateTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		this.texture = new Texture(pixmap);
		pixmap.dispose();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		this.motion.y += 0.1;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		Color col = getColor();
		Color beforeColor = batch.getColor();
		batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
		batch.draw(this.texture, getX(), getY());
		batch.setColor(beforeColor);
	}
}
