package tres;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tres.utils.Colors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tres.client.Client;

import java.io.IOException;
import java.io.InputStream;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static Client client;

	public static ApplicationThread applicationThread;

	protected static Logger logger;

	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(30);
		config.setTitle("Tres Application");
		TresApplication game = new TresApplication(config);

		applicationThread = new ApplicationThread(game, config);

		logger = LoggerFactory.getLogger(DesktopLauncher.class);
		client = new Client();
		long startMillis = System.currentTimeMillis();


		applicationThread.start();
		logger.info("Started");

		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream("build_info.json");

			ObjectMapper mapper = new ObjectMapper();

			JsonNode json = mapper.readTree(stream);

			logger.info("Built at: " + json.get("built_at").textValue());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// initDiscord();

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			// updateRPC(startMillis);
			if (client.isClosed() || (applicationThread.isDisposed() && applicationThread.getApplication().isDisposed())) {
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
