package com.dc0d.iiridarts.venture.networking;

import java.util.HashMap;

import com.dc0d.iiridarts.venture.Venture;
import com.dc0d.iiridarts.venture.World;
import com.dc0d.iiridarts.venture.handlers.RandomString;

public class NetworkObject {
	private String id;
	public Venture venture;
	private Byte objectType;
	private short subtype;
	public HashMap<String, Object> values;
	
	/**
	 * This constructor takes an id - used by the other constructor - not recommended outside of this class
	 * @param venture
	 * @param objectType
	 * @param subtype
	 * @param id
	 */
	
	public NetworkObject(Venture venture, byte objectType, byte subtype, String id) {
		this.venture = venture;
		this.setObjectType(objectType);
		this.setSubtype(subtype);
		this.id = id;
		values = new HashMap<String, Object>();
		
	}
	
	/**
	 * This constructor generates an id for you - use this instead of the other constructor
	 * @param venture
	 * @param objectType
	 * @param subtype
	 */
	
	public NetworkObject(Venture venture, byte objectType, byte subtype) {
		this(venture, objectType, subtype, new RandomString(5).nextString());
	}

	public Byte getObjectType() {
		return objectType;
	}

	public void setObjectType(Byte objectType) {
		this.objectType = objectType;
	}

	public short getSubtype() {
		return subtype;
	}

	public void setSubtype(Byte subtype) {
		this.subtype = subtype;
	}
	
	public String getFullId(){
		return id;
	}
}
