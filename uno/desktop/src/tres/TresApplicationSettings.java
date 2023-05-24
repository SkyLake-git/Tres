package tres;

import com.badlogic.gdx.Gdx;

import java.awt.*;

public class TresApplicationSettings {

	private final float refreshRate;

	private final int width;

	private final int height;

	protected int foregroundFPS;

	protected boolean vsync;

	protected float sensitivity;

	protected boolean anonymous;

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
		this.sensitivity = 1f;
		this.vsync = false;
		this.anonymous = false;
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

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public float getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
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

	public void setForegroundFPS(int fps) {
		this.foregroundFPS = fps;

		Gdx.graphics.setForegroundFPS(fps);
	}
}
