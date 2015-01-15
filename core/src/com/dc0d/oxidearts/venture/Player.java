/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.oxidearts.venture.physics.PhysicsBody;

/**
 * Holds information for player
 * @author Thomas Howe
 *
 */
public class Player extends Entity {
	
	public Sprite sprite;
	private World world;
	
	public Player(World world) {
		sprite = new Sprite(world.game.res.getTexture("player_male"));
		//TODO Work on Player stuff
		this.setPosition(400*16, 501*16);
		this.world = world;
	}
	
	public void updatePlayer(float delta){
		
		float timestep = 0.15f;
		
		float gx = Constants.GRAVITY_X * (timestep);
		float gy = Constants.GRAVITY_Y * (timestep);
		float drag = 0.001F;
		
		Vector2 oldAcceleration = new Vector2(acceleration);
		Vector2 avgAcceleration  = new Vector2();
		
		float maxVelocity = 75;
		
        int tileDimensions = 8;

        int leftX = (int)getLeft() / tileDimensions;
        int topY = (int)getTop() / tileDimensions;
        int rightX = (int)Math.ceil((float)getRight() / tileDimensions - 1);
        int bottomY = (int)Math.ceil(((float)getBottom() / tileDimensions) - 1);
        force.x = mass * Constants.GRAVITY_X - (drag * velocity.x);   
        force.y = mass * Constants.GRAVITY_Y - (drag * velocity.y);
        for(int i = 0; i < forces.size(); i++){
	        force.x += forces.get(i).x * timestep;
	        force.y += forces.get(i).y * timestep;
        }
        forces.clear();
        acceleration.x = force.x / mass;
        acceleration.y = force.y / mass;
        if(world.tileAt((int)position.x/16, (int)position.y/16).getType() > 0||world.tileAt(((int)position.x/16+1), (int)position.y/16).getType() > 0){
        velocity.x = MathUtils.clamp(velocity.x + acceleration.x * (timestep), -maxVelocity, maxVelocity);
        velocity.y = MathUtils.clamp(velocity.y + acceleration.y * (timestep), -maxVelocity, maxVelocity);
        position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
        position.y = (float)((int)(position.y/16)*16);//MathUtils.clamp(position.y + (velocity.y * (timestep)),(float)((int)(position.y/16)*16)+16, (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);
        } else {
		velocity.x = MathUtils.clamp(velocity.x + acceleration.x * (timestep), -maxVelocity, maxVelocity);
        velocity.y = MathUtils.clamp(velocity.y + acceleration.y * (timestep), -maxVelocity, maxVelocity);
        position.x = MathUtils.clamp(position.x + (velocity.x * (timestep)), world.game.camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-Constants.WORLDEDGEMARGIN);
        position.y = MathUtils.clamp(position.y + (velocity.y * (timestep)), Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*16)-(world.game.camera.viewportHeight)-Constants.WORLDEDGEMARGIN);
        }
        
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
	
}
