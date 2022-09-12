package utils;

public class PrefixedLogger extends MainLogger {

	protected String prefix;

	public PrefixedLogger(String prefix, String id) {
		super(id);
		this.prefix = prefix;
	}

	public PrefixedLogger(String prefix) {
		super("Main");
		this.prefix = prefix;
	}

	@Override
	public void info(String message) {
		super.info(this.prefix + message);
	}
}
