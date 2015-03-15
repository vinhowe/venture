/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.networking;

import java.util.HashMap;

import com.dc0d.iiridarts.venture.tiles.Tile;

public class TileUpdatePacket extends Packet{
	HashMap<Integer, HashMap<Integer, Tile>> tileUpdates;
	public TileUpdatePacket() {
		
	}
}
