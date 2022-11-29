package com.tres;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TresApplication extends Game {

	protected boolean isDisposed;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		this.viewport = new ScreenViewport();
		this.setScreen(new TresMainScreen(this, viewport));

		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render() {
		super.render();

		this.batch.begin();
		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		this.batch.setColor(0, 1f, 0.2f, 0.5f);

		float delta = Gdx.graphics.getDeltaTime();

		CharSequence str = "FPS: " + String.format("%.1f", 1 / delta);
		this.font.draw(this.batch, str, 0, this.viewport.getScreenHeight() - 5);

		this.batch.end();
		this.shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		this.viewport.update(width, height, false);
		this.batch.dispose();
		this.batch = new SpriteBatch();

		this.shapeRenderer.dispose();
		this.shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void dispose() {
		super.dispose();

		this.isDisposed = true;
	}

	public boolean isDisposed() {
		return isDisposed;
	}
}
