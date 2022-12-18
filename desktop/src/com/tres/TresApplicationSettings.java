package com.tres;

import com.badlogic.gdx.Gdx;

import java.awt.*;

public class TresApplicationSettings {

	protected int foregroundFPS;

	private final float refreshRate;

	public TresApplicationSettings() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = ge.getScreenDevices()[0];

		float refreshRate = device.getDisplayMode().getRefreshRate();
		if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
			this.refreshRate = 1;
		} else {
			this.refreshRate = refreshRate;
		}

		this.foregroundFPS = (int) this.refreshRate;
	}

	public float getMonitorRefreshRate() {
		return this.refreshRate;
	}

	public void setForegroundFPS(int fps) {
		this.foregroundFPS = fps;

		Gdx.graphics.setForegroundFPS(fps);
	}

	public int getForegroundFPS() {
		return foregroundFPS;
	}
}
