package com.tres.client.network;

import com.tres.client.ClientSession;
import com.tres.client.event.packet.DataPacketReceiveEvent;
import com.tres.network.packet.DataPacket;
import com.tres.promise.Promise;

public class PacketResponsePromise<REQ extends DataPacket, RES extends DataPacket> {

	protected ClientSession session;
	protected RES result;

	protected Class<RES> responseClass;

	protected REQ request;

	protected String eventRegistererId;

	protected Promise<RES> promise;

	public PacketResponsePromise(ClientSession session, REQ request, Class<RES> responseClass) {
		this.request = request;
		this.result = null;
		this.responseClass = responseClass;
		this.session = session;
		this.promise = null;
	}

	public RES getResult() {
		return result;
	}

	public Class<RES> getResponseClass() {
		return responseClass;
	}

	public REQ getRequest() {
		return request;
	}

	public Promise<RES> getPromise() {
		return promise;
	}

	public Promise<RES> start() throws Exception {
		if (this.promise != null) {
			throw new Exception("already started");
		}

		this.promise = new Promise<RES>();

		this.eventRegistererId = this.session.getClient().getEventEmitter().on(DataPacketReceiveEvent.class, (channel, event) -> {
			if (event.getPacket().getClass().equals(this.responseClass)) {
				this.result = (RES) event.getPacket();

				this.promise.resolve(this.result);
				this.session.getClient().getEventEmitter().off(this.eventRegistererId);
			}
		});

		return this.promise;
	}

	public void cancel() {
		if (this.promise == null) {
			return;
		}

		if (this.promise.isResolved()) {
			return;
		}

		this.promise.reject();
	}

	public void close() {
		this.session.getClient().getEventEmitter().off(this.eventRegistererId);
	}
}
