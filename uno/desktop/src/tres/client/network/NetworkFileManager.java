package tres.client.network;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class NetworkFileManager {

	protected HashMap<String, NetworkFile> files;

	public NetworkFileManager() {
		this.files = new HashMap<>();
	}

	public void add(NetworkFile file) {
		this.files.put(file.getStreamInfo().identifier, file);
	}

	public void remove(String identifier) {
		this.files.remove(identifier);
	}

	public @Nullable NetworkFile get(String identifier) {
		return this.files.get(identifier);
	}
}
