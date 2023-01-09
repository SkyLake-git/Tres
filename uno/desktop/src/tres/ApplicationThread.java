package tres;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ApplicationThread extends Thread {

	protected TresApplication application;

	protected Lwjgl3Application window;

	protected Lwjgl3ApplicationConfiguration configuration;

	protected boolean disposed;

	public ApplicationThread(TresApplication application, Lwjgl3ApplicationConfiguration configuration) {
		this.application = application;
		this.configuration = configuration;
		this.disposed = false;

		configuration.setForegroundFPS(this.application.getSettings().getForegroundFPS());
	}

	@Override
	public void run() {
		this.window = new Lwjgl3Application(this.application, this.configuration);
		this.disposed = true;
	}

	public TresApplication getApplication() {
		return application;
	}

	public Lwjgl3ApplicationConfiguration getConfiguration() {
		return configuration;
	}

	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public void interrupt() {
		super.interrupt();

		if (this.window != null) {
			this.window.exit();
		}
	}
}
