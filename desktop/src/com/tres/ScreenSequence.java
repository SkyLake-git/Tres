package com.tres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class ScreenSequence extends ScreenAdapter {

	protected Stage stage;

	protected TresApplication game;

	public ScreenSequence(TresApplication game, Viewport viewport) {
		this.stage = new Stage(viewport);
		this.game = game;

		Gdx.input.setInputProcessor(this.stage);

		this.init();
	}

	abstract protected void init();

	public Stage getStage() {
		return stage;
	}

	public Viewport getViewport() {
		return stage.getViewport();
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height, false);
	}

	@Override
	public void dispose() {
		this.stage.dispose();
	}
}
