package com.dc0d.thoriumlabs.venture;

import com.dc0d.thoriumlabs.venture.physics.PhysicsBody;

/**
 * Handler for sentient/dynamic things
 * @author Thomas
 *
 */
public class Entity extends PhysicsBody {
	
	short type;
	boolean isDead = false; //We dispose of dead things. Sorry.
	int health; // We use an int so we can bit shift and find special debuffs.
	
	public Entity() {
		super(PhysicsBody.BodyType.DynamicBody); // All entities should be dynamic unless rocks count as living
		//TODO Work on entity stuff
	}

}
