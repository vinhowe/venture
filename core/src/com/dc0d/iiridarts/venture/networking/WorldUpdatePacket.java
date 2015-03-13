package com.dc0d.iiridarts.venture.networking;

import com.dc0d.iiridarts.venture.handlers.Clock;

public class WorldUpdatePacket extends Packet {
	Clock clock;
	public WorldUpdatePacket() {
		super();
		clock = new Clock();
	}
}
