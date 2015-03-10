package com.dc0d.iiridarts.venture.networking;

import com.dc0d.iiridarts.venture.handlers.Clock;

public class WorldUpdate extends NetworkUpdate {
	Clock clock;
	public WorldUpdate() {
		super((byte) 1);
		clock = new Clock();
	}
}
