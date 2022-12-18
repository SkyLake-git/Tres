package com.tres.sequence;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.ScreenSequence;
import com.tres.TresApplication;
import com.tres.client.ui.actor.BorderlessButtonActor;
import com.tres.client.ui.actor.PinSliderActor;

import java.util.ArrayList;

public class SettingsScreen extends ScreenSequence {

	protected OrthographicCamera camera;

	protected PinSliderActor slider;

	protected Screen returnTo;

	protected BorderlessButtonActor applyButton;

	protected BorderlessButtonActor returnButton;

	public SettingsScreen(TresApplication game, Viewport viewport, Screen returnTo) {
		super(game, viewport);
		this.returnTo = returnTo;
	}

	@Override
	protected void init() {
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());

		this.slider = new PinSliderActor(
				new Vector2(0, 0),
				300,
				40,
				5,
				320,
				1,
				false,
				new BitmapFont(),
				"FPS: %.0f"
		);


		this.slider.setValue(this.game.getSettings().getForegroundFPS());

		ArrayList<Integer> list = new ArrayList<>();
		list.add(144);
		list.add(60);
		list.add(30);

		boolean found = false;
		for (int value : list) {
			Color color = new Color(1f, 1f, 1f, 1f);
			if (Math.abs(value - this.game.getSettings().getMonitorRefreshRate()) <= 1) {
				color = new Color(1f, 0f, 0f, 1f);
				found = true;
			}

			this.slider.addPin(new PinSliderActor.SliderPin(value, 5, color));
		}

		if (!found) {
			this.slider.addPin(new PinSliderActor.SliderPin(this.game.getSettings().getMonitorRefreshRate(), 5, new Color(1f, 0f, 0f, 1f)));
		}

		this.applyButton = new BorderlessButtonActor(
				new Vector2(120, -200),
				new BorderlessButtonActor.Button(
						"Apply",
						200,
						40,
						new Color(0, 0, 0, 1f),
						0.25f
				)
		);

		this.returnButton = new BorderlessButtonActor(
				new Vector2(-120, -200),
				new BorderlessButtonActor.Button(
						"Return",
						200,
						40,
						new Color(0, 0, 0, 1f),
						0f
				)
		);

		this.getViewport().setCamera(this.camera);

		this.stage.addActor(this.slider);
		this.stage.addActor(this.applyButton);
		this.stage.addActor(this.returnButton);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);

		this.stage.act(delta);
		this.stage.draw();

		if (this.applyButton.isPressed()) {
			this.game.getSettings().setForegroundFPS((int) this.slider.getValue());
		}

		if (this.returnButton.isPressed()) {
			this.game.setScreen(this.returnTo);
		}
	}


}
