/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture.handlers;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Utilities {

	  private final Random rnd = new Random();
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static TextureRegion fixBleeding(TextureRegion region) {
		float x = region.getRegionX();
		float y = region.getRegionY();
		float width = region.getRegionWidth();
		float height = region.getRegionHeight();
		float invTexWidth = 1f / region.getTexture().getWidth();
		float invTexHeight = 1f / region.getTexture().getHeight();
		region.setRegion((x + .5f) * invTexWidth, (y+.5f) * invTexHeight, (x + width - .5f) * invTexWidth, (y + height - .5f) * invTexHeight);  
		return region;
	}
	
	public static int lerp(int start, int end, float t)
	{
		t = MathUtils.clamp(t, 0, 1);
		return (int) (start + (end - start) * t);
	}
	
	/**
	 * Returns true if n is positive and false if it is not
	 * @param n
	 * @return
	 */
	public static boolean isPositive(float n){
		if(n > 0){
			return true;
		} else if (n < 0){
			return false;
		} else {
			return true;
		}
	}
}