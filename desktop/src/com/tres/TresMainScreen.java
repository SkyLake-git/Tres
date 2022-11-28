package com.tres;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tres.client.ui.actor.ToastNotificationActor;

public class TresMainScreen extends ScreenAdapter {

	protected Stage stage;

	protected OrthographicCamera camera;

	protected BitmapFont font;

	protected SpriteBatch batch;

	public TresMainScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.stage = new Stage(new ScreenViewport(camera));
		this.font = new BitmapFont();
		this.font.setColor(0, 0.9f, 0, 0.8f);
		this.batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		this.batch.begin();

		ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f);
		CharSequence str = "FPS: " + String.format("%.1f", 1 / delta);
		this.font.draw(this.batch, str, 0, this.stage.getHeight() - 5);
		this.stage.act(delta);
		this.stage.draw();

		this.batch.end();

		this.camera.update();
	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height, true);

		this.stage.addActor(new ToastNotificationActor(new Vector2(100, this.stage.getHeight() - 50), 200, 50, new Color(1, 0, 0, 1)));
	}

	protected void refreshBatch() {
		this.batch.dispose();
		this.batch = new SpriteBatch();
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		this.batch.dispose();
	}
}
