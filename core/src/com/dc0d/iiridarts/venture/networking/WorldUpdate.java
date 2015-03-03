package com.dc0d.iiridarts.venture.networking;

import com.dc0d.iiridarts.venture.handlers.Clock;

public class WorldUpdate extends UpdatePacket {
	Clock clock;
	public WorldUpdate() {
		clock = new Clock();
	}
}
