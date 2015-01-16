/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.oxidearts.venture.physics.PhysicsBody;

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
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight()-1);    
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
		
		float maxVelocity = 75;
		
        int tileDimensions = 8;

        int leftX = (int)getLeft() / tileDimensions;
        int topY = (int)getTop() / tileDimensions;
        int rightX = (int)Math.ceil((float)getRight() / tileDimensions - 1);
        int bottomY = (int)Math.ceil(((float)getBottom() / tileDimensions) - 1);
        if(world.tileAt((int)position.x/16, (int)(position.y/16)+1).getType() < 1&&world.tileAt(((int)position.x/16), (int)(position.y/16)-1).getType() > 0){
        velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity);
        velocity.y = MathUtils.clamp(velocity.y + Constants.GRAVITY_Y * (timestep), 0, maxVelocity);
        position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
        position.y = MathUtils.clamp(position.y + (velocity.y * (timestep)), (float)((int)(((position.y)/16))*16), (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);//MathUtils.clamp(position.y + (velocity.y * (timestep)),(float)((int)(position.y/16)*16)+16, (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);
        } else {
		velocity.x = MathUtils.clamp(velocity.x + Constants.GRAVITY_X * (timestep), -maxVelocity, maxVelocity);
        velocity.y = MathUtils.clamp(velocity.y + Constants.GRAVITY_Y * (timestep), -maxVelocity, maxVelocity);
        position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
        position.y = MathUtils.clamp(position.y + (velocity.y * (timestep)), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);
        
        }
        
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
	
	public void jump(float height){
		jumpspeed += Constants.GRAVITY_Y * 0.2;
		if(jumpspeed > 16-dimensions.y){
			jumpspeed = 16-dimensions.y;
		}
		if(0>jumpspeed){
			this.velocity.y -= jumpspeed;
		}
		else if(jumpspeed>0){
			this.velocity.y += jumpspeed;
		}
	}
	
}
