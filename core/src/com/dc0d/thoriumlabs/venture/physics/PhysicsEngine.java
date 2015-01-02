package com.dc0d.thoriumlabs.venture.physics;

import java.util.ArrayList;

import com.dc0d.thoriumlabs.venture.Constants;

public class PhysicsEngine {
	
    float gx;
    float gy;

	
	private ArrayList<PhysicsEntity> entities = new ArrayList<PhysicsEntity>();
	
	public void step(float elapsed){
	    gx = Constants.GRAVITY_X * elapsed;
	    gy = Constants.GRAVITY_Y * elapsed;
		PhysicsEntity entity;
		for(int i = 0, length = entities.size(); i <= length; i++){
			
			entity = entities.get(i);
			
			switch(entity.bodyType){
				case DynamicBody:
					entity.vx += entity.ax * elapsed + gx;
	                entity.vy += entity.ay * elapsed + gy;
	                entity.x  += entity.vx * elapsed;
	                entity.y  += entity.vy * elapsed;
	                break;
				case KinematicBody:
	                entity.vx += entity.ax * elapsed;
	                entity.vy += entity.ay * elapsed;
	                entity.x  += entity.vx * elapsed;
	                entity.y  += entity.vy * elapsed;
	                break;
				
			}
		}
		//TODO Find out where player fits in here. I probably also need an array of dynamic entities
		//FIXME These types don't match because I don't have inputs. :P
		//ArrayList<PhysicsEntity> collisions = CollisionSolver.detectCollisions(dynamicEntity, collidables);
	}
	

	
}
