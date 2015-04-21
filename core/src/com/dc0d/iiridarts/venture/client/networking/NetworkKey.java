package com.dc0d.iiridarts.venture.client.networking;

import com.dc0d.iiridarts.venture.client.Venture;

public class GenericNetworkKey extends NetworkKey {

	public GenericNetworkKey(Venture venture, byte objectType, byte subtype) {
		super(venture, objectType, subtype);
		// TODO Migrate NetworkKey (NetworkObject) class to GenericNetworkKey subclass
	}

}
