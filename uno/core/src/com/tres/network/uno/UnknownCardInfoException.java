package com.tres.network.uno;

public class UnknownCardInfoException extends Exception {
	public UnknownCardInfoException() {
	}

	public UnknownCardInfoException(String message) {
		super(message);
	}

	public UnknownCardInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownCardInfoException(Throwable cause) {
		super(cause);
	}

	public UnknownCardInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
