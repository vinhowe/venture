/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture.physics;

import java.util.ArrayList;

import com.dc0d.iiridarts.venture.Constants;

public class CollisionSolver {
	
	public static ArrayList<PhysicsBody> detectCollisions(PhysicsBody dynamicEntity, ArrayList<PhysicsBody> collidables){
		ArrayList<PhysicsBody> entities = new ArrayList<PhysicsBody>();
		PhysicsBody entity;
		for(int i = 0, length = collidables.size(); i <= length; i++){
			entity = collideRectEntity(dynamicEntity, collidables.get(i));
			if(entity != null){
				entities.add(entity);
			}
		}
		
		if(!(entities.size() > 0)){
			return null;
		}
		return entities;
	}
	
	public static void resolveBatch(PhysicsBody dynamicEntity, ArrayList<PhysicsBody> collisions){
		for(int i = 0, length = collisions.size(); i <= length; i++){
			solveElasticCollision(dynamicEntity, collisions.get(i));
		}
	}
	
	public static boolean collideRect(PhysicsBody entity1, PhysicsBody entity2){
		
		// Store the collider and collidee edges
	    float l1 = entity1.getLeft();
	    float t1 = entity1.getTop();
	    float r1 = entity1.getRight();
	    float b1 = entity1.getBottom();
	    
	    float l2 = entity2.getLeft();
	    float t2 = entity2.getTop();
	    float r2 = entity2.getRight();
	    float b2 = entity2.getBottom();
		
	    // If the any of the edges are beyond any of the
	    // others, then we know that the box cannot be
	    // colliding
	    if (b1 < t2 || t1 > b2 || r1 < l2 || l1 > r2) {
	        return false;
	    }
	    
	    // If the algorithm made it here, it had to collide
		return true;
	}
	
	public static PhysicsBody collideRectEntity(PhysicsBody entity1, PhysicsBody entity2){
		
		// Store the collider and collidee edges
		float l1 = entity1.getLeft();
		float t1 = entity1.getTop();
		float r1 = entity1.getRight();
	    float b1 = entity1.getBottom();
	    
	    float l2 = entity2.getLeft();
	    float t2 = entity2.getTop();
	    float r2 = entity2.getRight();
	    float b2 = entity2.getBottom();
		
	    // If the any of the edges are beyond any of the
	    // others, then we know that the box cannot be
	    // colliding
	    if (b1 < t2 || t1 > b2 || r1 < l2 || l1 > r2) {
	        return null;
	    }
	    
	    // If the algorithm made it here, it had to collide
		return entity2;
	}
	
	public static void solveElasticCollision(PhysicsBody dynamicEntity, PhysicsBody staticEntity){
		float dMidX = dynamicEntity.getMidX();
		float dMidY = dynamicEntity.getMidY();
		float sMidX = staticEntity.getMidX();
		float sMidY = staticEntity.getMidY();
		
		int dx = (int)((sMidX - dMidX) / staticEntity.halfDimensions.x);
		int dy = (int)((sMidY - dMidY) / staticEntity.halfDimensions.y);
		
		int absDX = Math.abs(dx);
		int absDY = Math.abs(dy);
		
		// If the distance between the normalized x and y
	    // position is less than a small threshold (.1 in this case)
	    // then this object is approaching from a corner
	    if (Math.abs(absDX - absDY) < .1) {

	        // If the player is approaching from positive X
	        if (dx < 0) {

	            // Set the player x to the right side
	        	dynamicEntity.position.x = staticEntity.getRight();

	        // If the player is approaching from negative X
	        } else {

	            // Set the player x to the left side
	        	dynamicEntity.position.x = staticEntity.getLeft() - staticEntity.dimensions.x;
	        }

	        // If the player is approaching from positive Y
	        if (dy < 0) {

	            // Set the player y to the bottom
	        	dynamicEntity.position.y = staticEntity.getBottom();

	        // If the player is approaching from negative Y
	        } else {

	            // Set the player y to the top
	        	dynamicEntity.position.y = staticEntity.getTop() - dynamicEntity.dimensions.y;
	        }
	        
	        // Randomly select a x/y direction to reflect velocity on
	        if (Math.random() < .5) {

	            // Reflect the velocity at a reduced rate
	        	dynamicEntity.velocity.x = -dynamicEntity.velocity.x * staticEntity.getRestitution();

	            // If the object's velocity is nearing 0, set it to 0
	            // STICKY_THRESHOLD is set to .0004
	            if (Math.abs(dynamicEntity.velocity.x) < Constants.STICKY_THRESHOLD) {
	            	dynamicEntity.velocity.x = 0;
	            }
	        } else {

	        	dynamicEntity.velocity.y = -dynamicEntity.velocity.y * staticEntity.getRestitution();
	            if (Math.abs(dynamicEntity.velocity.y) < Constants.STICKY_THRESHOLD) {
	            	dynamicEntity.velocity.y = 0;
	            }
	        }

	    // If the object is approaching from the sides
	    } else if (absDX > absDY) {

	        // If the player is approaching from positive X
	        if (dx < 0) {
	        	dynamicEntity.position.x = staticEntity.getRight();

	        } else {
	        // If the player is approaching from negative X
	        	dynamicEntity.position.x = staticEntity.getLeft() - dynamicEntity.dimensions.x;
	        }
	        
	        // Velocity component
	        dynamicEntity.velocity.x = -dynamicEntity.velocity.x * staticEntity.getRestitution();

	        if (Math.abs(dynamicEntity.velocity.x) < Constants.STICKY_THRESHOLD) {
	        	dynamicEntity.velocity.x = 0;
	        }

	    // If this collision is coming from the top or bottom more
	    } else {

	        // If the player is approaching from positive Y
	        if (dy < 0) {
	        	dynamicEntity.position.y = staticEntity.getBottom();

	        } else {
	        // If the player is approaching from negative Y
	        	dynamicEntity.position.y = staticEntity.getTop() - dynamicEntity.dimensions.y;
	        }
	        
	        // Velocity component
	        dynamicEntity.velocity.y = -dynamicEntity.velocity.y * staticEntity.getRestitution();
	        if (Math.abs(dynamicEntity.velocity.y) < Constants.STICKY_THRESHOLD) {
	        	dynamicEntity.velocity.y = 0;
	        }
	    }
	}
}
