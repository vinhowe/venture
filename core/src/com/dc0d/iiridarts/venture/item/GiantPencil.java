package com.dc0d.iiridarts.venture.item;

import com.badlogic.gdx.graphics.Texture;
import com.dc0d.iiridarts.venture.World;

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
