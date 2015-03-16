/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.item;

import com.dc0d.iiridarts.venture.client.World;

public class Weapon extends Item{
	
	short damage;
	
	public Weapon(short health, short type, short damage, World world) {
		super(health, type, world);
		this.damage = damage;
	}
	
}
