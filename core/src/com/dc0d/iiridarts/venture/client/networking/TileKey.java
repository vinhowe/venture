/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import com.badlogic.gdx.math.Vector2;

public class TileKey extends NetworkKey {
	Vector2 position;
	public TileKey(Vector2 position) {
		super(position);
		this.position = position;
	}
}
