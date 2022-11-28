package com.tres.network.packet;

import java.util.Objects;

public final class NetworkSettings {
	private final boolean compression;

	public NetworkSettings(boolean compression) {
		this.compression = compression;
	}

	public boolean compression() {
		return compression;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		NetworkSettings that = (NetworkSettings) obj;
		return this.compression == that.compression;
	}

	@Override
	public int hashCode() {
		return Objects.hash(compression);
	}

	@Override
	public String toString() {
		return "NetworkSettings[" +
				"compression=" + compression + ']';
	}

}
