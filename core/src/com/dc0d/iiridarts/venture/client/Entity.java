/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.client.networking.EntityKey;

public abstract class Entity{
	
	// Height and width values
	
	public Vector2 dimensions = new Vector2(0, 0);
	
	// Half width and half height values for faster calculation
	
	public Vector2 halfDimensions = new Vector2(0, 0);
	
	// Position values
	
	public Vector2 position = new Vector2(0, 0);
	
	// Velocity values - current speed in each direction - can be negative
	
	public Vector2 velocity = new Vector2(0, 0);
	
	// Acceleration values - velocity added each loop in each direction - can be negative
	
	public Vector2 acceleration = new Vector2(0, 0);
	
	// Restitution, or "bounciness" of entity
	
	private int restitution;
	
	// Object mass for faster math and flexibility
	
	public float mass = 0.1F;
	
	//Object forces
	
	public ArrayList<Vector2> forces;
	
	//Combined object force
	
	public Vector2 force;
	
	public World world;
	public Sprite sprite;
    float stateTime;
	
    EntityKey entityKey;
    
	boolean isRemote = false;
    
	public Entity(World world, int width, int height, byte entityType){
		entityKey = new EntityKey(world.venture, (byte)3, (byte) entityType);
		
		// Setting the entity's width and height
		// Make this variable
		
		this.dimensions.x = width;
		this.dimensions.y = height;
		
		// Setting half sizes
		
		this.halfDimensions.x = this.dimensions.x / 2;
		this.halfDimensions.y = this.dimensions.y / 2;
		forces = new ArrayList<Vector2>();
		force = new Vector2();
	}
	
	/**
	 * Sets the restitution of entity
	 * @param restitution
	 */
	
	public void setRestitution(int restitution){
		this.restitution = restitution;
	}
	
	/**
	 * Returns the restitution value of entity
	 */
	
	public int getRestitution(){
		return this.restitution;
	}
	
	/**
	 * Returns position of entity
	 */
	
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Sets new position for entity
	 * @param x
	 * @param y
	 */
	
	public void setPosition(int x, int y) {
		position.set(x, y);
	}
	
	/**
	 * Sets new position for entity
	 * @param pos
	 */
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public float getTop(){
		return this.position.y + this.dimensions.y;
	}
	
	public float getLeft(){
		return this.position.x;
	}
	
	public float getRight(){
		return this.position.x + this.dimensions.x;
	}
	
	public float getBottom(){
		return this.position.y;
	}
	
	public float getMidX(){
		return (this.dimensions.x/2) + this.position.x;
	}
	
	public float getMidY(){
		return (this.dimensions.y/2) + this.position.y;
	}
	
	public void updateBounds(){
        this.halfDimensions.x = (float) (this.dimensions.x * .5);
        this.halfDimensions.y = (float) (this.dimensions.y * .5);
	}
	
	public void applyImpulse(Vector2 force){
		forces.add(force);
	}
	
	public void applyImpulse(float forceX, float forceY){
	    forces.add(new Vector2(forceX, forceY));
	}
}


