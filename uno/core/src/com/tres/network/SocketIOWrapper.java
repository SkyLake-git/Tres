package com.tres.network;

import java.io.IOException;
import java.net.Socket;

public class SocketIOWrapper implements IOWrapper {

	protected Socket socket;

	public SocketIOWrapper(Socket socket){
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void write(byte[] src) {
		try {
			this.socket.getOutputStream().write(src);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isOpen() {
		return !this.socket.isClosed();
	}
}
