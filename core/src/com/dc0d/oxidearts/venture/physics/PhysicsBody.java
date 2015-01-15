/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture.physics;

import java.awt.Color;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class PhysicsBody {
	
	// Height and width values
	
	Vector2 dimensions = new Vector2(0, 0);
	
	// Half width and half height values for faster calculation
	
	Vector2 halfDimensions = new Vector2(0, 0);
	
	// Position values
	
	public Vector2 position = new Vector2(0, 0);
	
	// Velocity values - current speed in each direction - can be negative
	
	public Vector2 velocity = new Vector2(0, 0);
	
	// Acceleration values - velocity added each loop in each direction - can be negative
	
	public Vector2 acceleration = new Vector2(0, 0);
	
	// Force values - used for changing acceleration
	
	public Vector2 force = new  Vector2(0,0); 
	
	// Collision solver and body types
	
	BodyType bodyType;
	
	// Restitution, or "bounciness" of entity
	
	private int restitution;
	
	// Object mass for faster math and flexibility
	
	public float mass = 0.1F;
	
	// Force array - Add these up
	
	public ArrayList<Vector2> forces;
	
	public PhysicsBody(BodyType bodyType){
		this.bodyType = bodyType;
		
		// Setting the entity's width and height
		// Make this variable
		
		this.dimensions.x = 32;
		this.dimensions.y = 48;
		
		// Setting half sizes
		
		this.halfDimensions.x = this.dimensions.x / 2;
		this.halfDimensions.y = this.dimensions.y / 2;
		
		// Setting up force array
		
		forces = new ArrayList<Vector2>();
		
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
		return this.position.y;
	}
	
	public float getLeft(){
		return this.position.x;
	}
	
	public float getRight(){
		return this.position.x + this.dimensions.x;
	}
	
	public float getBottom(){
		return this.position.y + this.dimensions.y;
	}
	
	public float getMidX(){
		return this.halfDimensions.x + this.position.x;
	}
	
	public float getMidY(){
		return this.halfDimensions.y + this.position.y;
	}
	
	public void updateBounds(){
        this.halfDimensions.x = (float) (this.dimensions.x * .5);
        this.halfDimensions.y = (float) (this.dimensions.y * .5);
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
	}
	
	public void applyImpulse(Vector2 force){
		applyImpulse(force.x, force.y);
	}
	
	public void applyImpulse(float forceX, float forceY){
		forces.add(new Vector2(forceX, forceY));
	}
}


