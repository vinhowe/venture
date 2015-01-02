package com.dc0d.thoriumlabs.venture.physics;

public final class PhysicsHandlers {

	static double applyFriction(float value, float friction, float dt)
	{
	    return value * Math.pow((1-friction), dt);
	}
	
	static double applyAcceleration(float value, float acceleration, float dt)
	{
	    return value * Math.pow(acceleration, dt);
	}
}
