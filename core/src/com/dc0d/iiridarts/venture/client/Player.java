/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.client.handlers.Utilities;
import com.dc0d.iiridarts.venture.client.item.ItemStack;
import com.dc0d.iiridarts.venture.client.networking.NetworkKey;

/**
 * Holds information for player
 * @author Thomas Howe
 *
 */
public class Player extends EntityLiving {
	
	public Sprite itemSprite;
    
    ItemStack[] items;
    
    HashMap<Vector2, Short> tileBreakBuffer; 
    
    byte holdingStack;
    
    public String name;
    
    boolean swingingSword = false;
    short swingingSwordRampPos = 0;
	
	public Player(World world, boolean canFly, int x, int y, boolean isRemote) {
		super(world, (int)(16*1.5), (int)(24*1.5), isRemote, (byte) 1);
		networkKey = new NetworkKey(world.venture, (byte)2, (byte) 0);
		sprite = new Sprite();
		sprite.setSize(this.dimensions.x, this.dimensions.y);
		//TODO Work on Player stuff
		this.setPosition(x, y);
		this.world = world;
		//sprite.setScale(1.25f,1.25f);
		canFly = false;
		sprite.setOrigin(this.dimensions.x/2, 0);
		sprite.setScale(2f);
		items = new ItemStack[48];
		for(int i = 0; i < 48; i++) {
			items[i] = new ItemStack((short)0,0);
		}
		holdingStack = 0;
        itemSprite = new Sprite(world.venture.res.getItemTexture(1));
        itemSprite.scale(2f);
        world.venture.itemSprites.add(itemSprite);
        tileBreakBuffer = new HashMap<Vector2, Short>();
	}
	
	//public Player(PlayerJoinPacket packet) {
		
	//}
	
	public void updateLivingEntity(float timestep){
        if(world.venture.movingx){
            if(world.venture.directionx)
            {
            	if(jump)
            	{
            		bodyforce.x = run?1.25f:0.75f;
            	} else {
            		bodyforce.x = run?1.00f:0.50f;
            	}
            	if(canWalk)
            	{
            	walk = true;
            	}
            	hdir = false;
        	}
            else
            {
            	if(jump)
            	{
            		bodyforce.x = -0.75f;
            		if(run) {
            			bodyforce.x = -1.50f;
            		}
            	} else {
            		bodyforce.x = -0.50f;
            		if(run) {
            			bodyforce.x = -1.00f;
            		}
            	}
            	if(canWalk)
            	{
            	walk = true;
            	}
            	hdir = true;
            	//camera.position.x = Math.max(camera.position.x - Gdx.graphics.getDeltaTime() * 300*8, camera.viewportWidth/2+Constants.WORLDEDGEMARGIN);
            }
        }
        if(world.venture.movingy) {
            if (world.venture.directiony)
            {
            	if(jump && jumping){
            		velocity.y += 45;
            		jumping = false;
            	}
            	
            	if(canFly)
            	{
            		velocity.y = Math.max(45, velocity.y);
            	}
        	}
            else
            {
            	
            }
    	}
        if(world.venture.shift) {
        	run = true;
        } else {
        	run = false;
        }
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
			stateTime += Gdx.graphics.getDeltaTime()*(run?1.5f:1);
		
		if(walk){
        	currentFrame = animation.getKeyFrame(stateTime, true);
        }
        sprite.setRegion(currentFrame);
        
        sprite.flip(hdir, false);
		
        walk = false;
        //FIXME Use dynamic item textures instead of just debugging with pencil
        if(!hdir){
 	        itemSprite.setOrigin(0f, 0f);
	        itemSprite.setPosition(this.position.x+(this.sprite.getWidth()/2), this.position.y+(this.sprite.getHeight()/2.5f));
	        itemSprite.setFlip(false, false);
	        if(itemSprite.getRotation() > -60 && swingingSword){
	        	itemSprite.rotate(-2.5f*(swingingSwordRampPos*0.15f));
	        	swingingSwordRampPos++;
	        	itemSprite.setAlpha((float) (1f-(swingingSwordRampPos*0.01)));
	        } else {
	        	swingingSword = false;
	        	itemSprite.setRotation(0);
	        	swingingSwordRampPos = 0;
	        	itemSprite.setAlpha(0.05f);
	        }
        } else {
 	        itemSprite.setOrigin(itemSprite.getWidth(), 0f);
        	itemSprite.setPosition(this.position.x-(this.sprite.getWidth()/2), this.position.y+(this.sprite.getHeight()/2.5f));
        	itemSprite.setFlip(true, false);
	        if(itemSprite.getRotation() < 60 && swingingSword){
	        	itemSprite.rotate(2.5f*(swingingSwordRampPos*0.15f));
	        	swingingSwordRampPos++;
	        	itemSprite.setAlpha((float) (1f-(swingingSwordRampPos*0.01)));
	        } else {
	        	swingingSword = false;
	        	itemSprite.setRotation(0);
	        	swingingSwordRampPos = 0;
	        	itemSprite.setAlpha(0.05f);
	        }
        }
	}
	
	/*public void remoteUpdatePlayer(EntityUpdatePacket update){
        
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
        
	}*/
	
	public void breakTile(int x, int y) {
		//TODO Make player tile reach dynamic based on items in itemstack
		//TODO Make different tiles take longer to destroy and some unbreakable
		if((int)(position.x/Constants.TILESIZE) - 4 < x && (int)(position.x/Constants.TILESIZE) + 4 > x &&
				(int)(position.y/Constants.TILESIZE) - 4 < y && (int)(position.y/Constants.TILESIZE) + 4 > y){
			if(tileBreakBuffer.containsKey(new Vector2(x, y))) {
				if(tileBreakBuffer.get(new Vector2(x, y)).shortValue() < 100){
					tileBreakBuffer.put(new Vector2(x, y), new Short((short) ((tileBreakBuffer.get(new Vector2(x, y)).shortValue()+6))));
					if(Math.random() < 0.05){
						world.tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2), true);
					}
				} else {
					world.tileAt(x, y).setType((short) 0);
				}
			} else {
				tileBreakBuffer.put(new Vector2(x, y), new Short((short) 0));
			}
		}
	}
	
	//public EntityUpdatePacket makeEntityUpdatePacket() {
	//	return new EntityUpdatePacket(position, velocity, stateTime, id);
	//}
	public void doPhysics(float timestep) {
		float drag = 0.98F;
		//float acceleration = 0.5f;
		
		//Vector2 lastPos = position;
		
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
