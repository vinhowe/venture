package com.dc0d.thoriumlabs.venture.physics;

import java.awt.Color;

import com.badlogic.gdx.math.Vector2;

public class PhysicsEntity {
	
	// Height and width values
	
	int width;
	int height;
	
	// Half width and half height values for faster calculation
	
	float halfWidth;
	float halfHeight;
	
	// Position values
	
	int x;
	int y;
	
	// Velocity values - current speed in each direction - can be negative
	
	int vx;
	int vy;
	
	// Acceleration values - velocity added each loop in each direction - can be negative
	
	int ax;
	int ay;
	
	// Collision solver and body types
	
	SolverType collisionType;
	BodyType bodyType;
	
	// Restitution, or "bounciness" of entity
	
	private int restitution;
	
	public PhysicsEntity(SolverType collisionType, BodyType bodyType){
		this.bodyType = bodyType;
		this.collisionType = collisionType;
		
		// Setting the entity's width and height
		// Make this variable
		
		this.width = 20;
		this.height = 20;
		
		// Setting half sizes
		
		this.halfWidth = this.width / 2;
		this.halfHeight = this.height / 2;
		
		
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
		return new Vector2(x,y);
	}
	
	/**
	 * Sets new position for entity
	 * @param x
	 * @param y
	 */
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets new position for entity
	 * @param pos
	 */
	
	public void setPosition(Vector2 pos) {
		this.x = (int)pos.x;
		this.y = (int)pos.y;
	}
	
	public int getTop(){
		return this.y;
	}
	
	public int getLeft(){
		return this.x;
	}
	
	public int getRight(){
		return this.x + this.width;
	}
	
	public int getBottom(){
		return this.y + this.height;
	}
	
	public float getMidX(){
		return this.halfWidth + this.x;
	}
	
	public float getMidY(){
		return this.halfHeight + this.y;
	}
	
	public void updateBounds(){
        this.halfWidth = (float) (this.width * .5);
        this.halfHeight = (float) (this.height * .5);
	}

		public enum BodyType {
			KinematicBody(0), DynamicBody(1);
	
			private int value;
	
			private BodyType (int value) {
				this.value = value;
			}
	
			public int getValue () {
				return value;
			}
		};
		
		public enum SolverType {
			Displace(0), Elastic(1);
	
			private int value;
	
			private SolverType (int value) {
				this.value = value;
			}
	
			public int getValue () {
				return value;
			}
		};
}


