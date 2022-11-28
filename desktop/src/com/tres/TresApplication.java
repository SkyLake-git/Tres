package com.tres;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class TresApplication extends Game {

	protected boolean isDisposed;
	protected OrthographicCamera camera;

	@Override
	public void create() {
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 600, 400);
		this.setScreen(new TresMainScreen(camera));
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
