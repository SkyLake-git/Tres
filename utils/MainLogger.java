package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainLogger {

	public String id;

	public MainLogger(String id) {
		this.id = id;
	}

	public void info(String message) {
		this.send(message, "INFO", Colors.WHITE_BRIGHT);
	}

	public void warning(String message) {
		this.send(message, "WARNING", Colors.YELLOW_BRIGHT);
	}

	public void emergency(String message) {
		this.send(message, "EMERGENCY", Colors.RED_BRIGHT);
	}

	protected void send(String message, String level, Colors levelColor) {
		System.out.println(Colors.wrap(this.getTimestamp(), Colors.CYAN_BRIGHT) + Colors.wrap(" [" + level + "/" + this.id + "] ", levelColor) + message);
	}

	public String getTimestamp() {
		Date date = new Date();
		return (new SimpleDateFormat("hh:mm:ss")).format(date);
	}
}
