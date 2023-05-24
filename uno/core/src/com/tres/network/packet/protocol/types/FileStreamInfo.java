package com.tres.network.packet.protocol.types;

public class FileStreamInfo {

	public int maxChunkSize;

	public int chunkCount;

	public String digest;

	public String identifier;

	public FileStreamInfo(int maxChunkSize, int chunkCount, String digest, String identifier) {
		this.maxChunkSize = maxChunkSize;
		this.chunkCount = chunkCount;
		this.digest = digest;
		this.identifier = identifier;
	}

	public int getMaxChunkSize() {
		return maxChunkSize;
	}

	public int getChunkCount() {
		return chunkCount;
	}

	public String getDigest() {
		return digest;
	}

	public String getIdentifier() {
		return identifier;
	}
}
