/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.util.HashMap;

public class NetworkArray {
	HashMap<Integer, Object> keyValuePairs;
	Object key;
	public NetworkArray() {
		keyValuePairs = new HashMap<Integer, Object>();
	}
}
