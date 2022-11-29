package com.tres.client.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureUtils {

	public static void drawCenterTexture(Batch batch, Texture texture, float x, float y) {
		batch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
	}

	public static Vector2 getCenter(Actor actor) {
		return new Vector2(actor.getX() + actor.getWidth() / 2, actor.getY() + actor.getHeight() / 2);
	}
}
