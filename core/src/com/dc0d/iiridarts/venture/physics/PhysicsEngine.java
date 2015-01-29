/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture.physics;

import java.util.ArrayList;

import com.dc0d.iiridarts.venture.Constants;

public class PhysicsEngine {
	
    float gx;
    float gy;
    
	private ArrayList<PhysicsBody> bodies = new ArrayList<PhysicsBody>();
	
	public void step(float elapsed){
	    gx = Constants.GRAVITY_X * elapsed;
	    gy = Constants.GRAVITY_Y * elapsed;
		PhysicsBody body;
		for(int i = 0, length = bodies.size(); i <= length; i++){
			
			body = bodies.get(i);
			
			switch(body.bodyType){
				case DynamicBody:
					body.velocity.x += body.acceleration.x * elapsed + gx;
	                body.velocity.y += body.acceleration.y * elapsed + gy;
	                body.position.x  += body.velocity.x * elapsed;
	                body.position.y  += body.velocity.y * elapsed;
	                break;
				case KinematicBody:
					body.velocity.x += body.acceleration.x * elapsed;
	                body.velocity.y += body.acceleration.y * elapsed;
	                body.position.x  += body.velocity.x * elapsed;
	                body.position.y  += body.velocity.y * elapsed;
	                break;
				
			}
		}
		//Find out where player fits in here. I probably also need an array of dynamic entities
		//These types don't match because I don't have inputs. :P
		//ArrayList<PhysicsBody> collisions = CollisionSolver.detectCollisions(dynamicEntity, collidables);
	}
	public void addBody(PhysicsBody body){
		bodies.add(body);
	}
	
	public void removeBody(PhysicsBody body){
		bodies.remove(body);
	}
	

	
}
