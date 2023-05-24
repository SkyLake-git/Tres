package tres.client.network;

import com.tres.network.packet.protocol.types.FileStreamInfo;
import com.tres.promise.Promise;
import com.tres.utils.Utils;
import tres.client.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FileChunkReceiver {

	protected FileStreamInfo info;

	protected boolean background;

	protected HashMap<Integer, byte[]> chunks;

	protected Promise<Void> promise;

	protected long firstReceivedTime;

	public FileChunkReceiver(FileStreamInfo info, boolean background) {
		this.info = info;
		this.background = background;

		this.chunks = new HashMap<>();
		this.promise = new Promise<>();
		this.firstReceivedTime = -1L;
	}

	public long getTimeElapsed() {
		return System.currentTimeMillis() - this.firstReceivedTime;
	}

	public FileStreamInfo getInfo() {
		return info;
	}

	public Promise<Void> getPromise() {
		return promise;
	}

	public int getCompleteChunkCount() {
		return this.chunks.size();
	}

	public boolean receive(int chunkIndex, byte[] chunk) {
		if (chunkIndex > (this.info.chunkCount - 1)) {
			return false;
		}

		if (chunk.length > this.info.maxChunkSize) {
			return false;
		}

		if (chunk.length == 0) {
			return false;
		}

		if (this.chunks.size() >= this.info.chunkCount) {
			return false;
		}

		if (this.firstReceivedTime < 0) {
			this.firstReceivedTime = System.currentTimeMillis();
		}

		this.chunks.put(chunkIndex, chunk);

		if (this.chunks.size() >= this.info.chunkCount) {
			this.promise.resolve(null);
		}

		return true;
	}

	public static class Builder {

		public static CompletableFuture<byte[]> build(FileChunkReceiver receiver, Client client) {
			return client.getThreadPool().supply(() -> {
				List<Integer> indexSorted = receiver.chunks.keySet().stream().sorted().collect(Collectors.toList());

				List<Byte> bytes = new ArrayList<>();

				for (int index : indexSorted) {
					byte[] chunk = receiver.chunks.get(index);

					for (byte b : chunk) {
						bytes.add(b);
					}
				}

				return Utils.toByteArray(bytes.stream().mapToInt(Byte::intValue));
			});
		}
	}
}
