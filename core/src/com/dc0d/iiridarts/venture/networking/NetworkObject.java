package com.dc0d.iiridarts.venture.networking;

import java.util.HashMap;

import com.dc0d.iiridarts.venture.World;
import com.dc0d.iiridarts.venture.handlers.RandomString;

public class NetworkObject {
	private String id;
	private HashMap<Object, Object> values;
	public NetworkObject(String id) {
		this.id = id;
	}
	//TODO Make a creator for NetworkObjects
	public static NetworkObject generateNetworkObject(World world, byte type) {
		return new NetworkObject(type + new RandomString(5).nextString());
	}
}
