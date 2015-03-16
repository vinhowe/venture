/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import com.badlogic.gdx.math.Vector2;

public class TileKey {
	Vector2 position;
	NetworkObject world;
	public TileKey(Vector2 position, NetworkObject world) {
		this.position = position;
		this.world = world;
	}
}