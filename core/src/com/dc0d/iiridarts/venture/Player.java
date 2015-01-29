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
		super((int)(16*2), (int)(56*2));
		sprite = new Sprite();
		sprite.setSize(this.dimensions.x,this.dimensions.y);
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
		
		
		
		float timestep = 0.4f;
		
		float gx = Constants.GRAVITY_X * (timestep);
		float gy = Constants.GRAVITY_Y * (timestep);
		float drag = 0.005F;
		
		Vector2 oldAcceleration = new Vector2(acceleration);
		Vector2 avgAcceleration  = new Vector2();
		
	    int leftX = (int)getLeft() / Constants.TILESIZE;
	    int topY = (int)getTop() / Constants.TILESIZE;
	    int rightX = (int)Math.ceil((float)getRight() / Constants.TILESIZE - 1);
	    int bottomY = (int)Math.ceil(((float)getBottom() / Constants.TILESIZE) - 1);
		
		float maxVelocity = 125;
		
        int tileDimensions = 8;

    	jump = true;
        //if(world.tileAt((int)position.x/16, (int)(position.y/16)-1).getType() > 0 || world.tileAt(((int)position.x/16)+1, (int)(position.y/16)-1).getType() > 0){
	    //    jump = true;
	    //    velocity.x = (float) (MathUtils.clamp(velocity.x + Constants.GRAVITY_X, -maxVelocity, maxVelocity) * 0.25);
	    //    velocity.y = MathUtils.clamp(velocity.y + Constants.GRAVITY_Y, 0, maxVelocity);
	    //    position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
	    //    position.y += (float)((int)(((position.y)/16))*16) - position.y;
	    //    if(world.tileAt((int)position.x/16, (int)(position.y/16)).getType() > 0 || world.tileAt(((int)position.x/16)+1, (int)(position.y/16)).getType() > 0){
	        	position.y += 16;
	     //   }
        //} else {
        	jump = false;
		velocity.x = (float) (MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity) * 0.95);
        velocity.y = MathUtils.clamp(velocity.y + Constants.GRAVITY_Y * (timestep), -maxVelocity, maxVelocity);
        //}
        position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
        position.y = MathUtils.clamp(position.y + (velocity.y * (timestep)), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);
        
        checkToMap();
        
        stateTime += Gdx.graphics.getDeltaTime();
        
        currentFrame = animation.getKeyFrame(stateTime, true);
        
        sprite.setRegion(currentFrame);
        
        /*if (velocity.y > 0)
        {
            for (int x = leftX; x <= rightX; x++)
            {
                for (int y = bottomY + 1; y <= (bottomY + 1) + (velocity.y / tileDimensions); y++)
                {
                    if (tiles.get(x).get(y) != null && !(tiles.get(x).get(y).getType() > 0))
                    {
                        Vector2 newVelocity = new Vector2(velocity.x, MathUtils.clamp(velocity.y, 0F, (float)tiles.get(x).get(y).getType()));
                        velocity = newVelocity;
                        break;
                    }
                }
            }
        }*/
	}
	
	public void movePlayer(){
		//position.x
	}
	
	public void checkToMap() {
		int i, x1, x2, y1, y2;
		i = (int) (dimensions.y > Constants.TILESIZE ? Constants.TILESIZE : dimensions.y);  
		
		// Test the horizontal movement first
		
		for (;;)
	    {
	        x1 = (int) ((position.x + velocity.x ) / Constants.TILESIZE);
	        x2 = (int) ((position.x + velocity.x + dimensions.x - 1) / Constants.TILESIZE);
	    
	        y1 = (int) ((position.y) / Constants.TILESIZE);
	        y2 = (int) ((position.y + velocity.y + dimensions.y - 1) / Constants.TILESIZE);
	        
	        System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
	        
	        if (x1 >= 0 && x2 < Constants.mediumMapDimesions.x && y1 >= 0 && y2 < Constants.mediumMapDimesions.y)
	        {
	            if (this.velocity.x > 0)
	            {
	                /* Trying to move right */
	        
	                if ((world.tileAt(y1,x2).isSolid()) || (world.tileAt(y2,x2).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	        
	                    position.x = x2 * Constants.TILESIZE;
	                    
	                    position.x -= dimensions.x + 1;
	        
	                    velocity.x = 0;
	                }
	            }
	        
	            else if (this.velocity.x < 0)
	            {
	                /* Trying to move left */
	        
	                if ((world.tileAt(y1,x1).isSolid()) || (world.tileAt(y2,x1).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	                    
	                    position.x = (x1 + 1) * Constants.TILESIZE;
	        
	                    velocity.x = 0;
	                }
	            }
	        }
	        
	        if (i == dimensions.y)
	        {
	            break;
	        }
	        
	        i += Constants.TILESIZE;
	        
	        if (i > dimensions.y)
	        {
	            i = (int) dimensions.y;
	        }
	    }
		
		/* Now test the vertical movement */
	    
	    i = (int) (dimensions.x > Constants.TILESIZE ? Constants.TILESIZE : dimensions.x);
	    
	    for (;;)
	    {
	        x1 = (int) ((position.x) / Constants.TILESIZE);
	        x2 = (int) ((position.x + dimensions.x) / Constants.TILESIZE);
	    
	        y1 = (int) ((position.y + velocity.y) / Constants.TILESIZE);
	        y2 = (int) ((position.y + velocity.y + dimensions.y) / Constants.TILESIZE);
	        
	        if (x1 >= 0 && x2 < Constants.mediumMapDimesions.x && y1 >= 0 && y2 < Constants.mediumMapDimesions.y)
	        {
	            if (velocity.y < 0)
	            {
	                /* Trying to move down */
	                
	                if ((world.tileAt(y1, x1).isSolid()) || (world.tileAt(y1, x2).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	                    
	                    position.y = (y2 + 1) * Constants.TILESIZE;
	                    position.x -= dimensions.y + 1;
	        
	                    velocity.y = 0;
	                    
	                   // jump = true;
	                    
	                    System.out.println("down");
	                }
	            }
	        
	            else if (velocity.y > 0)
	            {
	                /* Trying to move up */
	        
	                if ((world.tileAt(y2, x1).isSolid()) || (world.tileAt(y2, x2).isSolid()))
	                {
	                    /* Place the player as close to the solid tile as possible */
	        
	                    position.y = position.y = y2 * Constants.TILESIZE;
	        
	                    velocity.y = 0;
	                    System.out.println("up");
	                }
	            }
	        }
	        
	        if (i == dimensions.x)
	        {
	            break;
	        }
	        
	        i += Constants.TILESIZE;
	        
	        if (i > dimensions.x)
	        {
	            i = (int) dimensions.x;
	        }
	    }

	    /* Now apply the movement */

	    position.x += velocity.x;
	    position.y += velocity.y;
	    
	    if (position.x < 0)
	    {
	        position.x = 0;
	    }
	}
	
}
