package com.tres.network.packet.protocol.types;

public class PacketHandlingException extends RuntimeException{

	public PacketHandlingException() {
	}

	public PacketHandlingException(String message) {
		super(message);
	}

	public PacketHandlingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PacketHandlingException(Throwable cause) {
		super(cause);
	}

	public PacketHandlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
