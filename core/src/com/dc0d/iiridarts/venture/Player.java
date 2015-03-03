/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.handlers.RandomString;
import com.dc0d.iiridarts.venture.networking.EntityUpdatePacket;

/**
 * Holds information for player
 * @author Thomas Howe
 *
 */
public class Player extends Entity {
	
    private static final int        FRAME_COLS = 4;
	
	public Sprite sprite;
	private World world;
    Animation                       animation;
    Texture                         animationSheet;
    TextureRegion[]                 animationFrames;
    TextureRegion                   currentFrame;
    
    float stateTime;
    public String name;
    
    boolean jumping = false;
	
	public Player(World world, boolean canFly, int x, int y, boolean isRemote) {
		super((int)(16*1.5), (int)(24*1.5), isRemote);
		sprite = new Sprite();
		sprite.setSize(this.dimensions.x, this.dimensions.y);
		//TODO Work on Player stuff
		this.setPosition(x, y);
		this.world = world;
		//sprite.setScale(1.25f,1.25f);
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
		canFly = false;
		sprite.setScale(2);
	}
	
	//public Player(PlayerJoinPacket packet) {
		
	//}
	
	public void updatePlayer(float timestep){
        
        //System.out.println(velocity.x);
        if(world.venture.sloMo){
        for(int i = 0; i <= 5; i++) doPhysics(timestep/10);
        } else {
            for(int i = 0; i <= 5; i++) doPhysics(timestep/5);
        }
		if(jump)
		{
			bodyforce.x = (float) Math.max(bodyforce.x - 0.1, 0);
		} else {
			//
		}
		
		stateTime += Gdx.graphics.getDeltaTime();
        
		if(walk){
        	currentFrame = animation.getKeyFrame(stateTime, true);
        }
        sprite.setRegion(currentFrame);
        
        sprite.flip(hdir, false);
		
        walk = false;
        
	}
	
	public void remoteUpdatePlayer(EntityUpdatePacket update){
        
        stateTime += Gdx.graphics.getDeltaTime();
        if(walk){
        currentFrame = animation.getKeyFrame(stateTime, true);
        }
        sprite.setRegion(currentFrame);
        
        sprite.flip(hdir, false);
        
        //System.out.println(velocity.x);
        position = update.pos;
        velocity = update.velocity;
       // for(int i = 0; i <= 5; i++) doPhysics(timestep/5);
        //walk = false;
        
	}
	
	//public EntityUpdatePacket makeEntityUpdatePacket() {
	//	return new EntityUpdatePacket(position, velocity, stateTime, id);
	//}
	
	public void movePlayer(){
		//position.x
	}
	
	public void doPhysics(float timestep){
		float drag = 0.98F;
		//float acceleration = 0.5f;
		
		Vector2 lastPos = position;
		
		float maxVelocity = 125;
		
    	if(walk || !jump){
    		velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity);
    	} else {
    		velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity) * drag;
    	}
		
    	jump = false;
		
		velocity.y = MathUtils.clamp(velocity.y + (this.world.venture.gravity ? Constants.GRAVITY_Y : 0) * (timestep), -maxVelocity, maxVelocity);
		if(world.venture.physics){
    	{
    		//Moving Left
	        if (velocity.x < 0 || bodyforce.x < 0){
	        	while(world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getBottom()/Constants.TILESIZE)+1).isSolid() || world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getTop()/Constants.TILESIZE)-1).isSolid()){
	        		position.x += (int)(getLeft())-getLeft()+1;
	        		velocity.x = 0;
	        		bodyforce.x = 0;
	        		jump = true;
	        		walk = false;
	        	}
        	//Moving Right
	        } else if (velocity.x > 0 || bodyforce.x > 0){
	        	while(world.tileAt((int)(getRight()/Constants.TILESIZE), (int)(getBottom()/Constants.TILESIZE)+1).isSolid() || world.tileAt((int)(getRight()/Constants.TILESIZE), (int)(getTop()/Constants.TILESIZE)-1).isSolid()){
	        		position.x -= (int)(getRight())-getRight()+1;
	        		velocity.x = 0;
	        		bodyforce.x = 0;
	        		jump = true;
	        		walk = false;
	        	}
	        } else {
	        	canWalk = true;
	        }
    	}
        
    	{   
    		//Moving Down
	        if (velocity.y < 0){
	        	while(world.tileAt((int)getLeft()/Constants.TILESIZE, (int)getBottom()/Constants.TILESIZE).isSolid() || 
	        			world.tileAt((int)(getRight())/Constants.TILESIZE, (int)getBottom()/Constants.TILESIZE).isSolid()||world.tileAt((int)(getMidX())/Constants.TILESIZE, (int)getBottom()/Constants.TILESIZE).isSolid())
	        	{
	        		position.y += (int)(getBottom())-getBottom()+1;
	        		velocity.y = 0;
	        		jump = true;
	        	}
        	//Moving Up
	        } else if (velocity.y > 0){
	        	while(world.tileAt((int)(getLeft()/Constants.TILESIZE), ((int)getTop()/Constants.TILESIZE)).isSolid() ||
	        			world.tileAt((int)(getRight()/Constants.TILESIZE), ((int)getTop()/Constants.TILESIZE)).isSolid())
    			{
	        		position.y -= (int)(getTop())-getTop()+1;
	        		velocity.y = 0;
	        	}
	        }
	        if(world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getBottom()-1)/Constants.TILESIZE).isSolid() || 
        			world.tileAt((int)(getRight())/Constants.TILESIZE, (int)(getBottom()-1)/Constants.TILESIZE).isSolid()||
        			world.tileAt((int)(getMidX())/Constants.TILESIZE, (int)(getBottom()-1)/Constants.TILESIZE).isSolid()||
        			world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getBottom()-4)/Constants.TILESIZE).isSolid() || 
        			world.tileAt((int)(getRight())/Constants.TILESIZE, (int)(getBottom()-4)/Constants.TILESIZE).isSolid()||
        			world.tileAt((int)(getMidX())/Constants.TILESIZE, (int)(getBottom()-4)/Constants.TILESIZE).isSolid()||
        			world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getBottom()+1)/Constants.TILESIZE).isSolid() || 
        			world.tileAt((int)(getRight())/Constants.TILESIZE, (int)(getBottom()+1)/Constants.TILESIZE).isSolid()||
        			world.tileAt((int)(getMidX())/Constants.TILESIZE, (int)(getBottom()+1)/Constants.TILESIZE).isSolid()){
	        	jump = true;
	        }
    	}
	
		/*collision = doCollision(position.x, position.y, velocity.x, velocity.y, dimensions.x, dimensions.y, Constants.TILESIZE);
        
        
		velocity.x = MathUtils.clamp(collision.width, -maxVelocity, maxVelocity) * drag;
        velocity.y = MathUtils.clamp(collision.height, -maxVelocity, maxVelocity) * drag;*/
    	//System.out.println(velocity.x);
    	if(getTop() >= (Constants.mediumMapDimesions.y*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN * drag){
    		velocity.y = MathUtils.clamp(velocity.y, -maxVelocity, 0);
    	} else if (position.y == Constants.WORLDEDGEMARGIN){
    		velocity.y = MathUtils.clamp(velocity.y, 0, maxVelocity) * drag;	
    	}
    	position.x = MathUtils.clamp((position.x + (velocity.x) + bodyforce.x), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN-dimensions.x);
        position.y = MathUtils.clamp((position.y + (velocity.y * (timestep)) + bodyforce.y), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN-dimensions.y);
		} else { 
	    	position.x = MathUtils.clamp((position.x + (velocity.x) + bodyforce.x), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN-dimensions.x);
	        position.y = MathUtils.clamp((position.y + (velocity.y * (timestep)) + bodyforce.y), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN-dimensions.y);
		}
	}
}
