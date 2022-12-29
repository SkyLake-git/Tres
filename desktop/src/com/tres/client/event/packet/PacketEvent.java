package com.tres.client.event.packet;

import com.tres.event.BaseEvent;
import com.tres.network.packet.DataPacket;

public class PacketEvent extends BaseEvent {

	protected DataPacket packet;

	public PacketEvent(DataPacket packet) {
		super();

		this.packet = packet;
	}

	public DataPacket getPacket() {
		return packet;
	}
}

