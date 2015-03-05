package com.dc0d.iiridarts.venture.item;

import com.badlogic.gdx.graphics.Texture;
import com.dc0d.iiridarts.venture.World;

public class Weapon extends Item{
	
	short damage;
	
	public Weapon(short health, short type, short damage, World world) {
		super(health, type, world);
		this.damage = damage;
	}
	
}
