/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.handlers.RandomString;

/**
 * Handler for sentient/dynamic things
 * @author Thomas
 *
 */

public class EntityLiving extends Entity {
	
	short type;
	boolean isDead = false; //We dispose of dead things. Sorry.
	int health; // We use an integer so we can bit shift and find special debuffs.
	boolean jump = false; // Is our entity on the ground and able to jump?
	boolean canFly = false; // Is our entity a flying animal or something magical?
	boolean hdir = false;
	byte vdir = 1;
	boolean run = false;
	boolean walk = false;
	boolean canWalk = false;
	public boolean isRemote;
	
    Animation                       animation;
    Texture                         animationSheet;
    TextureRegion[]                 animationFrames;
    TextureRegion                   currentFrame;
	
	public Vector2 bodyforce;
	
	public EntityLiving(int width, int height, boolean isRemote) {
		super(Entity.BodyType.DynamicBody, width, height); // All entities should be dynamic unless rocks count as living
		this.isRemote = isRemote;
		RandomString rndString = new RandomString(10);
		id = rndString.nextString();
		System.out.println(id);
		bodyforce = new Vector2();
		//TODO Work on entity stuff
	}

}
