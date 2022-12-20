package com.tres;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tres.client.Client;
import com.tres.utils.Colors;
import com.tres.utils.MainLogger;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static Client client;

	public static ApplicationThread applicationThread;
	protected static MainLogger logger;

	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(30);
		config.setTitle("Tres Application");
		TresApplication game = new TresApplication(config);

		applicationThread = new ApplicationThread(game, config);

		logger = new MainLogger("Launcher");
		client = new Client();
		long startMillis = System.currentTimeMillis();


		applicationThread.start();
		logger.info("Started");

		// initDiscord();

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			// updateRPC(startMillis);
			if (client.isClosed() || applicationThread.isDisposed()) {
				logger.warn("Detected client/application thread closed");

				logger.info(Colors.wrap("Fetching status...", Colors.YELLOW_BOLD_BRIGHT));

				if (!client.isClosed()) {
					logger.warn("Client not closed. closing...");
					client.close();
				}

				if (!game.isDisposed()) {
					logger.warn("Application not disposed. disposing...");
					applicationThread.interrupt();
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
