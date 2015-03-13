package com.dc0d.iiridarts.venture.networking;

import java.util.HashMap;

import com.dc0d.iiridarts.venture.tiles.Tile;

public class TileUpdatePacket extends Packet{
	HashMap<Integer, HashMap<Integer, Tile>> tileUpdates;
	public TileUpdatePacket() {
		
	}
}
