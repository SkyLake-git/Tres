package tres;

import com.badlogic.gdx.Gdx;

import java.awt.*;

public class TresApplicationSettings {

	protected int foregroundFPS;

	protected boolean vsync;

	private final float refreshRate;

	private final int width;
	private final int height;

	public TresApplicationSettings() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = ge.getDefaultScreenDevice();

		float refreshRate = device.getDisplayMode().getRefreshRate();
		if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
			this.refreshRate = 1;
		} else {
			this.refreshRate = refreshRate;
		}

		this.width = device.getDisplayMode().getWidth();
		this.height = device.getDisplayMode().getHeight();

		this.foregroundFPS = (int) this.refreshRate;
		this.vsync = false;
	}

	public int getMonitorHeight() {
		return height;
	}

	public int getMonitorWidth() {
		return width;
	}

	public float getMonitorRefreshRate() {
		return this.refreshRate;
	}

	public void setForegroundFPS(int fps) {
		this.foregroundFPS = fps;

		Gdx.graphics.setForegroundFPS(fps);
	}

	public void useVsync(boolean vsync) {
		this.vsync = vsync;
		Gdx.graphics.setVSync(vsync);
	}

	public boolean isUsingVsync() {
		return this.vsync;
	}

	public int getForegroundFPS() {
		return foregroundFPS;
	}
}
