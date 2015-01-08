/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture.physics;

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
