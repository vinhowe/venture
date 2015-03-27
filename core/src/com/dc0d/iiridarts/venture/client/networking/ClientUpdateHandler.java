/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.util.HashMap;

public class ClientUpdateHandler {
	private HashMap<Object, HashMap<Integer, Object>> keyValueUpdates;
	
	public ClientUpdateHandler() {
		keyValueUpdates = new HashMap<Object, HashMap<Integer, Object>>();
	}
	
	public void addUpdate(Object object, int index, Object value) {
		if(keyValueUpdates.get(object) != null) {
			keyValueUpdates.get(object).put(index, value);
		} else {
			keyValueUpdates.put(object, new HashMap<Integer, Object>());
		}
	}
	
	public void clearUpdates() {
		keyValueUpdates.clear();
	}
}
