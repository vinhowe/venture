/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dc0d.oxidearts.venture.physics.PhysicsBody;

/**
 * Holds information for player
 * @author Thomas Howe
 *
 */
public class Player extends Entity {
	
	public Sprite sprite;
	
	public Player(World world) {
		sprite = new Sprite(world.game.res.getTexture("player_male"));
		//TODO Work on Player stuff
		this.setPosition(400*16, 501*16);
	}
	
}
