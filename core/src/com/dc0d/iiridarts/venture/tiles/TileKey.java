package com.dc0d.iiridarts.venture.tiles;

import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.networking.NetworkObject;

public class TileKey {
	Vector2 position;
	NetworkObject world;
	public TileKey(Vector2 position, NetworkObject world) {
		this.position = position;
		this.world = world;
	}
}
