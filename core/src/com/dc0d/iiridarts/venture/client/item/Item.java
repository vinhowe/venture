/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.item;

import com.badlogic.gdx.graphics.Texture;
import com.dc0d.iiridarts.venture.client.World;

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
