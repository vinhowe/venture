/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.handlers.RandomString;

/**
 * Handler for sentient/dynamic things
 * @author Thomas
 *
 */

public abstract class EntityLiving extends Entity {
	
	short type;
	boolean isDead = false; //We dispose of dead things. Sorry.
	int health; // We use an integer so we can bit shift and find special debuffs.
	boolean jump = false; // Is our entity on the ground and able to jump?
	boolean canFly = false; // Is our entity a flying animal or something magical?
	boolean hdir = false;
	byte vdir = 1;
	boolean canWalk = false;
	boolean walk = false;
	boolean run = false;
	public boolean jumping = false;
	
    private static final int        FRAME_COLS = 4;
	
    Animation                       animation;
    Texture                         animationSheet;
    TextureRegion[]                 animationFrames;
    TextureRegion                   currentFrame;
	
	public Vector2 bodyforce;
	
	public EntityLiving(World world, int width, int height, boolean isRemote, byte entityType) {
		super(world, width, height, entityType); // All entities should be dynamic unless rocks count as living
		this.isRemote = isRemote;
		RandomString rndString = new RandomString(10);
		//id = rndString.nextString();
		//System.out.println(id);
		bodyforce = new Vector2();
		//TODO Work on entity stuff
		animationSheet = new Texture(world.venture.res.getTexture("entity_2").getTextureData());
		animationFrames = new TextureRegion[4];
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight());    
		int index = 0;
		for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
		animation = new Animation(0.25f, animationFrames);
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, true);
	}
	
	abstract void doPhysics(float timestep);
	
	abstract void updateLivingEntity(float timestep);
}
