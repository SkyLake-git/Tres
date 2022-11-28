package com.tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class ToastNotificationActor extends Entity {

	public static class Toast {

		public float width;
		public float height;
		public double staySeconds;
		public Color color;

		public Toast(float width, float height, double staySeconds, Color color) {
			this.width = width;
			this.height = height;
			this.staySeconds = staySeconds;
			this.color = color;
		}
	}

	protected Texture texture;

	protected double staySeconds;

	protected double startStayMillis;

	public ToastNotificationActor(Vector2 position, Toast toast) {
		super(position);

		this.staySeconds = toast.staySeconds;

		setWidth(toast.width);
		setHeight(toast.height);

		recreateTexture((int) toast.width, (int) toast.height, toast.color);

		this.startStayMillis = -1;
	}

	private void recreateTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(0.1f, 0.1f, 0.1f, 1);
		pixmap.fillRectangle(0, 0, width, height);
		pixmap.setColor(color);
		pixmap.fillRectangle(2, 2, width, height);
		this.texture = new Texture(pixmap);
		pixmap.dispose();
	}

	@Override
	protected void init() {
		this.setMotion(new Vector2(0, -2));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		this.motion.y += 0.02;

		if (this.startStayMillis > 0) {
			double elapsed = System.currentTimeMillis() - this.startStayMillis;
			if (elapsed < (this.staySeconds * 1000)) {
				this.motion.y = 0f;
			}

			if (elapsed > ((this.staySeconds + 0.5) * 1000)) {
				this.remove();
			}
		} else {
			if (this.motion.y > 0) {
				this.startStayMillis = System.currentTimeMillis();
			}
		}
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
