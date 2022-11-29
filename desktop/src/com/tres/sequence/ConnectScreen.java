package com.tres.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.ScreenSequence;
import com.tres.TresApplication;
import com.tres.TresMainScreen;
import com.tres.client.AbsoluteDrawer;
import com.tres.client.ui.actor.BorderlessButtonActor;

public class ConnectScreen extends ScreenSequence {

	protected OrthographicCamera camera;

	protected AbsoluteDrawer absoluteDrawer;

	protected BorderlessButtonActor button;

	public ConnectScreen(TresApplication game, Viewport viewport) {
		super(game, viewport);
	}

	@Override
	protected void init() {
		this.absoluteDrawer = new AbsoluteDrawer();
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());
		//this.stage.setDebugUnderMouse(true);
		BorderlessButtonActor helloButton = new BorderlessButtonActor(
				new Vector2(0, 0),
				new BorderlessButtonActor.Button(
						"Hello",
						300,
						40,
						new Color(0, 0.0f, 0, 1),
						2
				)
		);

		BorderlessButtonActor testButton = new BorderlessButtonActor(
				new Vector2(0, 60),
				new BorderlessButtonActor.Button(
						"World",
						300,
						40,
						new Color(0, 0.0f, 0, 1),
						0
				)
		);

		this.stage.addActor(helloButton);
		this.stage.addActor(testButton);

		this.button = testButton;

		this.getViewport().setCamera(this.camera);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);

		this.stage.act(delta);
		this.stage.draw();

		this.absoluteDrawer.act(delta);
		this.absoluteDrawer.draw();

		if (this.button.isPressed()) {
			this.game.setScreen(new TresMainScreen(this.game, this.getViewport()));
		}
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
	}
}
