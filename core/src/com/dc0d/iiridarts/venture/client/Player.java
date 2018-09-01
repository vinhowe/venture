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
import com.dc0d.iiridarts.venture.client.networking.EntityKey;

/**
 * Holds information for player
 * @author Thomas Howe
 *
 */
public class Player extends EntityLiving {
	
	public Sprite itemSprite;
	public Sprite itemGlowSprite;
    
    ItemStack[] items;
    
    HashMap<Vector2, Short> tileBreakBuffer; 
    
    byte holdingStack;
    
    public String name;
    
    boolean swingingSword = false;
    short swingingSwordRampPos = 0;
	
	public Player(World world, boolean canFly, int x, int y, boolean isRemote) {
		super(world, (int)(16*1.1), (int)(24*1.1), isRemote, (byte) 1);
		entityKey = new EntityKey(world.venture, (byte)2, (byte) 0);
		sprite = new Sprite();
		sprite.setSize(this.dimensions.x*1.5f, this.dimensions.y*1.5f);
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
        itemSprite = new Sprite(world.venture.res.getItemTexture(2));
        itemSprite.scale(1f);
        world.venture.itemSprites.add(itemSprite);
        if(world.venture.res.getItemGlowTexture(2) != null){
        	System.out.println("Found Glow Texture");
	        itemGlowSprite = new Sprite(world.venture.res.getItemGlowTexture(2));
	        itemGlowSprite.scale(1f);
	        world.venture.itemGlowSprites.add(itemGlowSprite);
        }
        tileBreakBuffer = new HashMap<Vector2, Short>();
	}
	
	//public Player(PlayerJoinPacket packet) {
		
	//}
	
	public void updateLivingEntity(float timestep){
        float walkAcceleration = 0.25f;
		float runAcceleration = 5.0f;
		float walkSpeed = 1f;
		float runSpeed = 3f;
		run = world.venture.shift;
		float maxSpeed = run ? runSpeed : walkSpeed;

		float verticalSpeed = world.venture.gravity ? 60 : 10;



		if(world.venture.movingx){

			flipped = !world.venture.directionx;
			if(canWalk) {
				walk = true;
			}



			float acceleration = run ? runAcceleration : walkAcceleration;

			// Don't allow control over acceleration while in air

			if(Math.abs(bodyforce.x) > maxSpeed && !canJump && !canFly && world.venture.gravity) {
				acceleration = 0;
			}

			bodyforce.x = Math.max(Math.min(bodyforce.x + (world.venture.directionx ? acceleration : -acceleration), runSpeed), -runSpeed);
        }
        if(world.venture.movingy) {
            if (world.venture.directiony)
            {
            	if((canJump || !world.venture.gravity) && jumping){
            		velocity.y += verticalSpeed;
            	}
            	
            	if(canFly && jumping && world.venture.gravity)
            	{
            		velocity.y = Math.min(velocity.y+5f, verticalSpeed);
            	}
        	} else if (!world.venture.gravity) {
				velocity.y -= verticalSpeed;
			}
    	}

        if(world.venture.sloMo){
        	for(int i = 0; i <= 5; i++) doPhysics(timestep/10);
        } else {
            for(int i = 0; i <= 5; i++) doPhysics(timestep/5);
        }

        // Slow player down if on the ground
		if(!walk && canJump || !world.venture.gravity) {
			bodyforce.x = (float) (bodyforce.x * (world.venture.gravity ? 0.5 : 0.9));
		}
			stateTime += Gdx.graphics.getDeltaTime()*(run?1.5f:1);
		
		if(walk){
        	currentFrame = animation.getKeyFrame(stateTime, true);
        }
        sprite.setRegion(currentFrame);
        
        sprite.flip(flipped, false);
		
        walk = false;
        //FIXME Use dynamic item textures instead of just debugging with pencil
        if(!flipped){
 	        itemSprite.setOrigin(0f, 0f);
	        itemSprite.setPosition(this.position.x+(this.sprite.getWidth()/1.25f), this.position.y+(this.sprite.getHeight()/1.90f));
	        itemSprite.setFlip(false, false);
	        if(itemGlowSprite != null) {
				itemGlowSprite.setOrigin(0f, 0f);
				itemGlowSprite.setPosition(this.position.x + (this.sprite.getWidth() / 1.25f), this.position.y + (this.sprite.getHeight() / 1.90f));
				itemGlowSprite.setFlip(false, false);
			}
	        if(itemSprite.getRotation() > -60 && swingingSword){
	        	itemSprite.rotate(-2.5f*(swingingSwordRampPos*0.15f));
				if(itemGlowSprite != null) {
					itemGlowSprite.rotate(-2.5f * (swingingSwordRampPos * 0.15f));
				}
	        	swingingSwordRampPos++;
	        	//itemSprite.setAlpha((float) (1f-(swingingSwordRampPos*0.01)));
	        } else {
	        	swingingSword = false;
	        	itemSprite.setRotation(0);
	        	if(itemGlowSprite != null) {
					itemGlowSprite.setRotation(0);
				}
	        	swingingSwordRampPos = 0;
	        	//itemSprite.setAlpha(0.90f);
	        }
        } else {
 	        itemSprite.setOrigin(itemSprite.getWidth(), 0f);
        	itemSprite.setPosition(this.position.x-(this.sprite.getWidth()/1.25f), this.position.y+(this.sprite.getHeight()/1.90f));
        	itemSprite.setFlip(true, false);
			if(itemGlowSprite != null) {
				itemGlowSprite.setOrigin(itemGlowSprite.getWidth(), 0f);
				itemGlowSprite.setPosition(this.position.x - (this.sprite.getWidth() / 1.25f), this.position.y + (this.sprite.getHeight() / 1.90f));
				itemGlowSprite.setFlip(true, false);
			}
	        if(itemSprite.getRotation() < 60 && swingingSword){
	        	itemSprite.rotate(2.5f*(swingingSwordRampPos*0.15f));
				if(itemGlowSprite != null) {
					itemGlowSprite.rotate(2.5f * (swingingSwordRampPos * 0.15f));
				}
	        	swingingSwordRampPos++;
	        	//itemSprite.setAlpha((float) (1f-(swingingSwordRampPos*0.01)));
	        } else {
	        	swingingSword = false;
	        	itemSprite.setRotation(0);
				if(itemGlowSprite != null) {
					itemGlowSprite.setRotation(0);
				}
	        	swingingSwordRampPos = 0;
	        	//itemSprite.setAlpha(0.15f);
	        }
        }

    }
        
	
	/*public void remoteUpdatePlayer(EntityUpdatePacket update){
        
        stateTime += Gdx.graphics.getDeltaTime();
        if(walk){
        currentFrame = animation.getKeyFrame(stateTime, true);
        }
        sprite.setRegion(currentFrame);
        
        sprite.flip(flipped, false);
        
        //System.out.println(velocity.x);
        position = update.pos;
        velocity = update.velocity;
       // for(int i = 0; i <= 5; i++) doPhysics(timestep/5);
        //walk = false;
        
	}*/
	
	public void modifyTileDamage(int x, int y, int damage) {
		//TODO Make player tile reach dynamic based on items in itemstack
		//TODO Make different tiles take longer to destroy and some unbreakable
		int reach = 400;

		if((int)(position.x/Constants.TILESIZE) - reach < x && (int)(position.x/Constants.TILESIZE) + reach > x &&
				(int)(position.y/Constants.TILESIZE) - reach < y && (int)(position.y/Constants.TILESIZE) + reach > y){
			if(tileBreakBuffer.containsKey(new Vector2(x, y))) {
				if(tileBreakBuffer.get(new Vector2(x, y)).shortValue() < 100){
					tileBreakBuffer.put(new Vector2(x, y), new Short((short) ((tileBreakBuffer.get(new Vector2(x, y)).shortValue()+damage))));
					if(Math.random() < 0.05){
						world.tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2), true);
					}
				} else {
					if(damage > 0){
						world.tileAt(x, y).setType((short) 0);
					} else {
						//FIXME Use player item and not placeholder tile
						world.tileAt(x, y).setType((short) 2);
					}
				}
			} else {
				tileBreakBuffer.put(new Vector2(x, y), (short) 0);
			}
		}
	}
	
	//public EntityUpdatePacket makeEntityUpdatePacket() {
	//	return new EntityUpdatePacket(position, velocity, stateTime, id);
	//}
	public void doPhysics(float timestep) {
		float drag = 0.18F;
		//float acceleration = 0.5f;
		
		//Vector2 lastPos = position;
		
		float maxVelocity = 125;
		
		if(walk || !canJump){
			velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity);
		} else {
			velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity) * drag;
		}
		
		canJump = false;
		
		velocity.y = MathUtils.clamp(velocity.y + (this.world.venture.gravity ? -Constants.GRAVITY_Y : -(velocity.y * 0.70f)) * (timestep), -maxVelocity, maxVelocity);
		if(world.venture.physics){
		{
			//Moving Left
	        if (velocity.x < 0 || bodyforce.x < 0){
	        	while(world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getBottom()/Constants.TILESIZE)+1).isSolid() || world.tileAt((int)getLeft()/Constants.TILESIZE, (int)(getTop()/Constants.TILESIZE)-1).isSolid()){
	        		position.x += (int)(getLeft())-getLeft()+1;
	        		velocity.x = 0;
	        		bodyforce.x = 0;
	        		canJump = true;
	        		//walk = false;
	        	}
	    	//Moving Right
	        } else if (velocity.x > 0 || bodyforce.x > 0){
	        	while(world.tileAt((int)(getRight()/Constants.TILESIZE), (int)(getBottom()/Constants.TILESIZE)+1).isSolid() || world.tileAt((int)(getRight()/Constants.TILESIZE), (int)(getTop()/Constants.TILESIZE)-1).isSolid()){
	        		position.x -= (int)(getRight())-getRight()+1;
	        		velocity.x = 0;
	        		bodyforce.x = 0;
	        		canJump = true;
	        		//walk = false;
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
	        		canJump = true;
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
	        	canJump = true;
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
