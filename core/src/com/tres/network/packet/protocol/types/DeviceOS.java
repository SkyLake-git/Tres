package com.tres.network.packet.protocol.types;

public enum DeviceOS {


	UNKNOWN(-1),
	OSX(3),
	LINUX(4),
	SUN(5),

	WINDOWS(17);


	public int id;

	DeviceOS(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static DeviceOS parse(String os) {
		if (os.startsWith("Windows")) {
			return DeviceOS.WINDOWS;
		}

		if (os.startsWith("Linux")) {
			return DeviceOS.LINUX;
		}

		if (os.startsWith("Mac OS X")) {
			return DeviceOS.OSX;
		}

		if (os.startsWith("SunOS")) {
			return DeviceOS.SUN;
		}

		return DeviceOS.UNKNOWN;
	}
}
