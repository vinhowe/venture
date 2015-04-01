/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-April 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.util.ArrayList;

public class NetworkArray {

	// CLEANUP HashMap with Integer and UpdateValuePair may be better as an
	// arraylist
	private ArrayList<UpdateValuePair> keyValuePairs;
	Object key;

	public NetworkArray(Object key) {
		keyValuePairs = new ArrayList<UpdateValuePair>();
	}

	public void flagUpdate(int index, boolean update) {
		keyValuePairs.get(index).update = true;
	}

	public void toggleUpdateFlag(int index) {
		keyValuePairs.get(index).update = !keyValuePairs.get(index).update;
	}

	public void addVariable() {

	}

}
