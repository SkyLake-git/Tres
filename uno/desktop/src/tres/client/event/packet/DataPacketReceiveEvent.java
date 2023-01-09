package tres.client.event.packet;

import com.tres.network.packet.DataPacket;

public class DataPacketReceiveEvent extends PacketEvent {
	public DataPacketReceiveEvent(DataPacket packet) {
		super(packet);
	}
}
