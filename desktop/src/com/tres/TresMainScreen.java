package com.tres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.client.AbsoluteDrawer;
import com.tres.client.ui.actor.ToastNotificationActor;
import com.tres.sequence.ConnectScreen;

public class TresMainScreen extends ScreenSequence {

	protected OrthographicCamera camera;

	protected BitmapFont font;

	protected SpriteBatch batch;

	protected AbsoluteDrawer absoluteDrawer;

	public TresMainScreen(TresApplication game, Viewport viewport) {
		super(game, viewport);
	}


	@Override
	protected void init() {
		this.absoluteDrawer = new AbsoluteDrawer();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, getViewport().getScreenWidth(), getViewport().getScreenHeight());

		this.stage = new Stage(this.getViewport());
		this.font = new BitmapFont();
		this.font.setColor(0, 0.9f, 0, 0.8f);
		this.batch = new SpriteBatch();

		this.getViewport().setCamera(this.camera);
	}

	@Override
	public void render(float delta) {
		this.batch.begin();

		ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);
		this.stage.act(delta);
		this.stage.draw();
		this.absoluteDrawer.act(delta);
		this.absoluteDrawer.draw();

		this.batch.end();

		this.camera.update();

		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			this.addToastNotification(new ToastNotificationActor.Toast(200, 50, "Hello, World", 1, new Color(1, 1, 1, 0.75f)));
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			this.game.setScreen(new ConnectScreen(this.game, this.stage.getViewport()));
			this.dispose();
		}

	}

	public void addToastNotification(ToastNotificationActor.Toast toast) {
		this.absoluteDrawer.addActor(new ToastNotificationActor(new Vector2(this.stage.getWidth() / 2, this.stage.getHeight() + toast.height - 5), toast));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		this.absoluteDrawer.refreshBatch();
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
		super.dispose();
		this.batch.dispose();
	}
}
