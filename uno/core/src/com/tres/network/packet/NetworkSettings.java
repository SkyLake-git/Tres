package com.tres.network.packet;

public final class NetworkSettings {

	public static final int BUFFER_SIZE = 1024 * 1024 * 4;

	private final boolean compression;

	private final boolean encryption;

	public NetworkSettings(boolean compression, boolean encryption) {
		this.compression = compression;
		this.encryption = encryption;
	}

	public boolean compression() {
		return compression;
	}

	public boolean encryption() {
		return encryption;
	}

}
