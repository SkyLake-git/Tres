package com.tres;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tres.client.Client;
import com.tres.utils.Colors;
import com.tres.utils.MainLogger;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	protected static Client client;
	protected static MainLogger logger;

	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(30);
		config.setForegroundFPS(144);
		config.setTitle("Tres");
		TresApplication game = new TresApplication();
		Lwjgl3Application application = new Lwjgl3Application(game, config);

		logger = new MainLogger("Launcher");
		client = new Client();
		long startMillis = System.currentTimeMillis();

		logger.info("Started");

		// initDiscord();

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			// updateRPC(startMillis);
			if (client.isClosed() || game.isDisposed()) {
				logger.warn("Detected client thread closed");

				logger.info(Colors.wrap("Fetching gui/client status...", Colors.YELLOW_BOLD_BRIGHT));

				if (!client.isClosed()) {
					logger.warn("Client not closed. closing...");
					client.close();
				}

				break;
			}
		}

		// DiscordRPC.discordShutdown();
		logger.info("Closed discord");

		logger.info(Colors.wrap("See you...", Colors.BLUE_BOLD_BRIGHT));

		System.exit(0);
	}
}
