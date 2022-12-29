package com.tres.client.event.packet;

import com.tres.network.packet.DataPacket;

public class DataPacketSendEvent extends PacketEvent {
	public DataPacketSendEvent(DataPacket packet) {
		super(packet);
	}
}
