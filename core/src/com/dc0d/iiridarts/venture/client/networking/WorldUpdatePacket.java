/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import com.dc0d.iiridarts.venture.client.handlers.Clock;

public class WorldUpdatePacket extends Packet {
	Clock clock;
	public WorldUpdatePacket() {
		super();
		clock = new Clock();
	}
}
