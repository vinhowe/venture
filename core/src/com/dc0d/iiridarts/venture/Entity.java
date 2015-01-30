/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture;

import com.dc0d.iiridarts.venture.physics.PhysicsBody;

/**
 * Handler for sentient/dynamic things
 * @author Thomas
 *
 */

public class Entity extends PhysicsBody {
	
	short type;
	boolean isDead = false; //We dispose of dead things. Sorry.
	int health; // We use an int so we can bit shift and find special debuffs.
	int jumpHeight = 5; //Default jump height
	boolean jump = false;
	float jumpspeed;
	float jumpstart = -18;
	byte thinkTime = 0;
	
	public Entity(int width, int height) {
		super(PhysicsBody.BodyType.DynamicBody, width, height); // All entities should be dynamic unless rocks count as living
		//TODO Work on entity stuff
	}

}
