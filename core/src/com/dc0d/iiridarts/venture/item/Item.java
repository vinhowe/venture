package com.dc0d.iiridarts.venture.item;

import com.badlogic.gdx.graphics.Texture;
import com.dc0d.iiridarts.venture.World;

public class Item {
	short health;
	short type;
	Texture texture;
	World world;
	
	public Item(short health, short type, World world) {
		this.health = health;
		this.type = type;
		this.world = world;
	}
}
