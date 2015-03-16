/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.item;

import com.dc0d.iiridarts.venture.client.World;

public class GiantPencil extends Weapon {
	
	private final static short type = 1; 
	//Texture texture;
	
	public GiantPencil(short health, World world) {
		super(health, type, health, world);
		//texture = world.venture.res.getTexture(key);
		
	}

	public short getType() {
		return type;
	}

}
