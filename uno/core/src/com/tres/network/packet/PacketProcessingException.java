package com.tres.network.packet;

public class PacketProcessingException extends Exception {

	public PacketProcessingException() {
	}

	public PacketProcessingException(String message) {
		super(message);
	}

	public PacketProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PacketProcessingException(Throwable cause) {
		super(cause);
	}

	public PacketProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
