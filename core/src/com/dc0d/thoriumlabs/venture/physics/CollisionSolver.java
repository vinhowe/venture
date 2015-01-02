package com.dc0d.thoriumlabs.venture.physics;

import java.util.ArrayList;

import com.dc0d.thoriumlabs.venture.Constants;

public class CollisionSolver {
	
	public static ArrayList<PhysicsEntity> detectCollisions(PhysicsEntity dynamicEntity, ArrayList<PhysicsEntity> collidables){
		ArrayList<PhysicsEntity> entities = new ArrayList<PhysicsEntity>();
		PhysicsEntity entity;
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
	
	public static void resolveBatch(PhysicsEntity dynamicEntity, ArrayList<PhysicsEntity> collisions){
		for(int i = 0, length = collisions.size(); i <= length; i++){
			solveElasticCollision(dynamicEntity, collisions.get(i));
		}
	}
	
	public static boolean collideRect(PhysicsEntity entity1, PhysicsEntity entity2){
		
		// Store the collider and collidee edges
	    int l1 = entity1.getLeft();
	    int t1 = entity1.getTop();
	    int r1 = entity1.getRight();
	    int b1 = entity1.getBottom();
	    
	    int l2 = entity2.getLeft();
	    int t2 = entity2.getTop();
	    int r2 = entity2.getRight();
	    int b2 = entity2.getBottom();
		
	    // If the any of the edges are beyond any of the
	    // others, then we know that the box cannot be
	    // colliding
	    if (b1 < t2 || t1 > b2 || r1 < l2 || l1 > r2) {
	        return false;
	    }
	    
	    // If the algorithm made it here, it had to collide
		return true;
	}
	
	public static PhysicsEntity collideRectEntity(PhysicsEntity entity1, PhysicsEntity entity2){
		
		// Store the collider and collidee edges
	    int l1 = entity1.getLeft();
	    int t1 = entity1.getTop();
	    int r1 = entity1.getRight();
	    int b1 = entity1.getBottom();
	    
	    int l2 = entity2.getLeft();
	    int t2 = entity2.getTop();
	    int r2 = entity2.getRight();
	    int b2 = entity2.getBottom();
		
	    // If the any of the edges are beyond any of the
	    // others, then we know that the box cannot be
	    // colliding
	    if (b1 < t2 || t1 > b2 || r1 < l2 || l1 > r2) {
	        return null;
	    }
	    
	    // If the algorithm made it here, it had to collide
		return entity2;
	}
	
	public static void solveElasticCollision(PhysicsEntity dynamicEntity, PhysicsEntity staticEntity){
		float dMidX = dynamicEntity.getMidX();
		float dMidY = dynamicEntity.getMidY();
		float sMidX = staticEntity.getMidX();
		float sMidY = staticEntity.getMidY();
		
		int dx = (int)((sMidX - dMidX) / staticEntity.halfWidth);
		int dy = (int)((sMidY - dMidY) / staticEntity.halfHeight);
		
		int absDX = Math.abs(dx);
		int absDY = Math.abs(dy);
		
		// If the distance between the normalized x and y
	    // position is less than a small threshold (.1 in this case)
	    // then this object is approaching from a corner
	    if (Math.abs(absDX - absDY) < .1) {

	        // If the player is approaching from positive X
	        if (dx < 0) {

	            // Set the player x to the right side
	        	dynamicEntity.x = staticEntity.getRight();

	        // If the player is approaching from negative X
	        } else {

	            // Set the player x to the left side
	        	dynamicEntity.x = staticEntity.getLeft() - staticEntity.width;
	        }

	        // If the player is approaching from positive Y
	        if (dy < 0) {

	            // Set the player y to the bottom
	        	dynamicEntity.y = staticEntity.getBottom();

	        // If the player is approaching from negative Y
	        } else {

	            // Set the player y to the top
	        	dynamicEntity.y = staticEntity.getTop() - dynamicEntity.height;
	        }
	        
	        // Randomly select a x/y direction to reflect velocity on
	        if (Math.random() < .5) {

	            // Reflect the velocity at a reduced rate
	        	dynamicEntity.vx = -dynamicEntity.vx * staticEntity.getRestitution();

	            // If the object's velocity is nearing 0, set it to 0
	            // STICKY_THRESHOLD is set to .0004
	            if (Math.abs(dynamicEntity.vx) < Constants.STICKY_THRESHOLD) {
	            	dynamicEntity.vx = 0;
	            }
	        } else {

	        	dynamicEntity.vy = -dynamicEntity.vy * staticEntity.getRestitution();
	            if (Math.abs(dynamicEntity.vy) < Constants.STICKY_THRESHOLD) {
	            	dynamicEntity.vy = 0;
	            }
	        }

	    // If the object is approaching from the sides
	    } else if (absDX > absDY) {

	        // If the player is approaching from positive X
	        if (dx < 0) {
	        	dynamicEntity.x = staticEntity.getRight();

	        } else {
	        // If the player is approaching from negative X
	        	dynamicEntity.x = staticEntity.getLeft() - dynamicEntity.width;
	        }
	        
	        // Velocity component
	        dynamicEntity.vx = -dynamicEntity.vx * staticEntity.getRestitution();

	        if (Math.abs(dynamicEntity.vx) < Constants.STICKY_THRESHOLD) {
	        	dynamicEntity.vx = 0;
	        }

	    // If this collision is coming from the top or bottom more
	    } else {

	        // If the player is approaching from positive Y
	        if (dy < 0) {
	        	dynamicEntity.y = staticEntity.getBottom();

	        } else {
	        // If the player is approaching from negative Y
	        	dynamicEntity.y = staticEntity.getTop() - dynamicEntity.height;
	        }
	        
	        // Velocity component
	        dynamicEntity.vy = -dynamicEntity.vy * staticEntity.getRestitution();
	        if (Math.abs(dynamicEntity.vy) < Constants.STICKY_THRESHOLD) {
	        	dynamicEntity.vy = 0;
	        }
	    }
	}
}
