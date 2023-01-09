package com.tres.network.unet;

import java.io.IOException;
import java.net.Socket;

public class USocket {

	private final Socket socket;

	public USocket(String host, int port) {
		try {
			this.socket = new Socket(host, port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void readPacket() {
	}
}
