package client;

import client.ui.gui.Uno;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import utils.Colors;
import utils.MainLogger;
import utils.PrefixedLogger;

public class Main {

	public static Uno uno;
	public static Client client;

	public static MainLogger logger;

	public static void initDiscord() {
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
			MainLogger localLogger = logger;
			localLogger.info("Logged in discord: welcome " + user.username + "#" + user.discriminator);
		}).build();
		DiscordRPC.discordInitialize("1020678310663028757", handlers, true);
		logger.info("Initialized Discord");
	}

	public static void updateRPC(long startMillis) {
		DiscordRichPresence rpc = new DiscordRichPresence.Builder("Playing uno: 0/0 players").setStartTimestamps(startMillis).build();
		DiscordRPC.discordUpdatePresence(rpc);
	}

	public static void main(String[] args) throws InterruptedException {
		logger = new PrefixedLogger("", "Process");
		client = new Client();
		uno = new Uno();

		long startMillis = System.currentTimeMillis();

		initDiscord();

		while (true) {
			Thread.sleep(1000);
			updateRPC(startMillis);
			if (client.isClosed() || uno.getPanel().isClosed()) {
				logger.warning("Detected panel/client thread closed");

				logger.info(Colors.wrap("Fetching gui/client status...", Colors.YELLOW_BOLD_BRIGHT));

				if (!client.isClosed()) {
					logger.warning("Client not closed. closing...");
					client.close();
				}

				if (!uno.getPanel().isClosed()) {
					logger.warning("MainPanel not closed. closing...");
					uno.getPanel().close();
					logger.info("Disposed gui");
					uno.dispose();
				}

				break;
			}
		}

		DiscordRPC.discordShutdown();
		logger.info("Closed discord");

		logger.info(Colors.wrap("See you...", Colors.BLUE_BOLD_BRIGHT));

		System.exit(0);
	}
}
