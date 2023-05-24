package tres.client.network;

import com.tres.network.packet.protocol.types.FileStreamInfo;

public class NetworkFile {

	protected FileStreamInfo streamInfo;

	protected byte[] content;

	public NetworkFile(FileStreamInfo streamInfo, byte[] content) {
		this.streamInfo = streamInfo;
		this.content = content;
	}

	public FileStreamInfo getStreamInfo() {
		return streamInfo;
	}

	public byte[] getContent() {
		return content.clone();
	}
}
