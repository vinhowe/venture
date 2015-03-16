/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.util.HashMap;

public class ClientTileHandler {
	HashMap<TileKey, HashMap<Integer, Object>> tileUpdates;
	
	public ClientTileHandler() {
		tileUpdates = new HashMap<TileKey, HashMap<Integer, Object>>();
	}
	
	public void addTileUpdate() {
		
	}
}
