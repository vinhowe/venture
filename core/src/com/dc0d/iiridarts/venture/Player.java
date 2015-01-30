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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Holds information for player
 * @author Thomas Howe
 *
 */
public class Player extends Entity {
	
    private static final int        FRAME_COLS = 6;
	
	public Sprite sprite;
	private World world;
    Animation                       animation;
    Texture                         animationSheet;
    TextureRegion[]                 animationFrames;
    TextureRegion                   currentFrame;
    
    float stateTime;
	
	public Player(World world) {
		super((int)(32), (int)(32));
		sprite = new Sprite();
		sprite.setSize(this.dimensions.x, this.dimensions.y);
		//TODO Work on Player stuff
		this.setPosition(400*16, 501*16);
		this.world = world;
		//sprite.setScale(1.25f,1.25f);
		animationSheet = new Texture(world.game.res.getTexture("player_male").getTextureData());
		animationFrames = new TextureRegion[4];
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight());    
		int index = 0;
		for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
		animation = new Animation(0.075f, animationFrames);
	}
	
	public void updatePlayer(float delta){
		
		Rectangle collision; 
		
		float timestep = 0.4f;
		
		float gx = Constants.GRAVITY_X * (timestep);
		float gy = Constants.GRAVITY_Y * (timestep);
		float drag = 0.98F;
		
		float maxVelocity = 125;
		
		int x1 = (int) (position.x / 16);
		int x2 = (int) (position.x / 16) + Constants.TILESIZE;
		
		int y1 = (int) (position.y / 16);
		int y2 = (int) (position.y / 16) + Constants.TILESIZE;
				
    	jump = true;
		velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity) * drag;
        velocity.y = MathUtils.clamp(velocity.y + Constants.GRAVITY_Y * (timestep), -maxVelocity, maxVelocity) * drag;
        
        position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
        position.y = MathUtils.clamp(position.y + (velocity.y * (timestep)), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);
        

        if (velocity.y < 0){
        	if(world.tileAt(x1, y1).isSolid()){
                //while(world.tileAt(x1, y1).isSolid()){
	        	position.y += (y1*16+Constants.TILESIZE)-position.y-1;
	        	y1 = (int) (position.y / 16);
	        	velocity.y = MathUtils.clamp(velocity.y + Constants.GRAVITY_Y * (timestep), 0 , maxVelocity);
	        	//}
	        	while(world.tileAt(x1, y1+1).isSolid()){
        		position.y += Constants.TILESIZE;
        		y1+=1;
	        	}
	        }
        }
        /*collision = doCollision(position.x, position.y, velocity.x, velocity.y, dimensions.x, dimensions.y, Constants.TILESIZE);
        
		velocity.x = MathUtils.clamp(collision.width, -maxVelocity, maxVelocity) * drag;
        velocity.y = MathUtils.clamp(collision.height, -maxVelocity, maxVelocity) * drag;*/
        
        
        stateTime += Gdx.graphics.getDeltaTime();
        
        currentFrame = animation.getKeyFrame(stateTime, true);
        
        sprite.setRegion(currentFrame);
	}
	
	public void movePlayer(){
		//position.x
	}
	
	public Rectangle doCollision(float x, float y, float vx, float vy, float w, float h, int tilesize) {
		int x1, x2, y1, y2;
		
			// Test the horizontal movement first
	        x1 = (int) ((x) / tilesize);
	        x2 = (int) ((x + w) / tilesize);
	    
	        y1 = (int) ((y) / tilesize);
	        y2 = (int) ((y + h) / tilesize);
        	
	        if(world.tileAt(x1, y1).isSolid())
    		{
	        world.game.logger.finer("tile at X:" + x1 +" Y:"+ y1 + " is solid");
	        } else {
        	world.game.logger.finer("tile at X:" + x1 +" Y:"+ y1 + " is not solid");
	        }
	       
	       
	            if (vx > 0)
	            {
	                // Trying to move right
	        
	                if ((world.tileAt(y1,x2).isSolid()) || (world.tileAt(y2,x2).isSolid()))
	                {
	                    // Place the player as close to the solid tile as possible
	        
	                    x = x2;
	                    
	                    //x -= w + 1;
	        
	                    vx = 0;
	                    
	                    world.game.logger.config("tile at " + y1 + " is solid");
	                }
	            }
	        
	            else if (vx < 0)
	            {
	                /* Trying to move left */
	        
	                if ((world.tileAt(y1,x1).isSolid()) || (world.tileAt(y2,x1).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	                    
	                    x = (x1 + 1) * tilesize;
	        
	                    vx = 0;
	                }
	            }
		
		/* Now test the vertical movement */
	    
	        x1 = (int) ((x) / tilesize);
	        x2 = (int) ((x + w) / tilesize);
	    
	        y1 = (int) ((y) / tilesize);
	        y2 = (int) ((y + h) / tilesize);
	        
	       // if (x1 >= 0 && x2 < Constants.mediumMapDimesions.x && y1 >= 0 && y2 < Constants.mediumMapDimesions.y)
	       // {
	            if (vy < 0)
	            {
	                /* Trying to move down */
	                
	                if ((world.tileAt(y1, x1).isSolid()) || (world.tileAt(y1, x2).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	                    
	                    y += ((y1*tilesize)+tilesize)-y;
	                    y -= h + 1;
	        
	                    vy = 0;
	                    
	                   // jump = true;
	                }
	            }
	        
	            else if (vy > 0)
	            {
	                /* Trying to move up */
	        
	                if ((world.tileAt(y2, x1).isSolid()) || (world.tileAt(y2, x2).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	        
	                    y = y2 * tilesize;
	        
	                    vy = 0;
	                }
	            }
	       // }

	    /* Now apply the movement */

	   // x += vx;
	    //y += vy;
	    
	    return new Rectangle(x, y, vx, vy);
	    
	   // if (x < 0)
	   // {
	   //     x = 0;
	   // }
	}
	
}
