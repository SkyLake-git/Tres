package com.tres;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.sequence.SettingsScreen;

public class TresApplication extends Game {

	protected boolean isDisposed;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected ShapeRenderer shapeRenderer;

	protected TresApplicationSettings settings;

	public TresApplication(Lwjgl3ApplicationConfiguration config) {
		this.settings = new TresApplicationSettings();
	}

	public TresApplicationSettings getSettings() {
		return settings;
	}

	@Override
	public void create() {
		this.viewport = new ScreenViewport();
		this.setScreen(new TresMainScreen(this, viewport));

		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);

		if (screen instanceof ScreenSequence) {
			((ScreenSequence) screen).focus();
		}
	}

	@Override
	public void render() {
		super.render();

		this.batch.begin();
		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		this.batch.setColor(0, 1f, 0.2f, 0.5f);

		float delta = Gdx.graphics.getDeltaTime();

		CharSequence str = "FPS: " + Gdx.graphics.getFramesPerSecond();
		this.font.draw(this.batch, str, 0, this.viewport.getScreenHeight() - 5);
		this.font.draw(this.batch, "F: " + String.format("%.0f", 1 / delta), 100, this.viewport.getScreenHeight() - 5);

		this.batch.end();
		this.shapeRenderer.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			if (!(this.getScreen() instanceof SettingsScreen)) {
				this.setScreen(new SettingsScreen(this, this.viewport, this.getScreen()));
			}
		}
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
