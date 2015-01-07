package com.dc0d.thoriumlabs.venture;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.thoriumlabs.venture.handlers.Utilities;
import com.dc0d.thoriumlabs.venture.physics.PhysicsBody;
import com.dc0d.thoriumlabs.venture.tiles.Tile;

/**
 * The World class contains world information including tiles and entities.
 * @author Thomas Howe
 *
 */
public class World {
	
	ArrayList<ArrayList<Tile>> tiles;
	private String name;
	private final byte type;
	
	public World(String name, byte type){
		this.setName(name);
		this.type = type;
		tiles = new ArrayList<ArrayList<Tile>>();
	}
	
	/**
	 * This method draws the tiles in the world
	 * @param spriteBatch
	 */
	 public void draw(SpriteBatch spriteBatch)
	    {
		 	int startx = (int)Math.floor((32) / 16);
	        int endx = (int)Math.ceil((1024 + 32) / 16);
	        int starty = (int)Math.floor((32) / 16);
	        int endy = (int)Math.ceil(( 768 + 32) / 16);
	
	        for (int x = startx; x < endx; x += 1)
	        {
	            for (int y = starty; y < endy; y += 1)
	            {
	                Tile tile = this.tileAt(x, y);
	                if (tile != null){
	                	//TODO Remove this function or find out how to implement it
	            	}
	
	            }
	        }
	    }
	 
	 public Tile tileAt(int x, int y){
		 if(x>0&&y>0&&x<(int)Constants.mediumMapDimesions.x&&y<(int)Constants.mediumMapDimesions.y){
		 return tiles.get(x).get(y);
	 	}
		 else return new Tile((short)-1,(byte)0,(byte)0,(byte)0);
	 }
	 
	 public Vector2 tileTex(int x, int y){
		 return tileAt(x,y).getTexCoords();
	 }
	 
	 public byte tileTexX(int x, int y){
		 return tileAt(x,y).getTexX();
	 }
	 
	 public byte tileTexY(int x, int y){
		 return tileAt(x,y).getTexY();
	 }
	 
	 public void generate(){
		 for(int x = 0; x < (int)Constants.mediumMapDimesions.x; x++){
			 tiles.add(x, new ArrayList<Tile>());
			 for(int y = 0; y < (int)Constants.mediumMapDimesions.y; y++){
				 tiles.get(x).add(y, new Tile((short)/*new Random().nextInt(2)*/1,(byte)0,(byte)new Random().nextInt(5),(byte)new Random().nextInt(5)));
				 tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2));
				 tiles.get(x).get(y);
			 }
		 }
		 updateAllTiles();
	}
	 
	public void updateAllTiles(){
		for(int x = 0; x < Constants.mediumMapDimesions.x; x++){
			for(int y = 0; y < Constants.mediumMapDimesions.y; y++){
				 updateTile(x,y);
			}
		 }
	}
	
	public void updateTile(int x, int y){
		if(x > 0&&y >0&&x<(int)Constants.mediumMapDimesions.x&&y<(int)Constants.mediumMapDimesions.y){
			if(tileAt(x,y) == null){
				return;
			} else {
				
					 //Left
					 if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(0,tileAt(x, y).getRandom()));
				 	 } 
					 //Upper left corner
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(tileAt(x, y).getRandom(), 3));
				 	 } 
					 //Upper
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(),0));
				 	 }
					 //Upper right corner
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(3+tileAt(x, y).getRandom(),3));
				 	 }
					 //Right
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(4,tileAt(x, y).getRandom()));
				 	 } 
					 //Lower right corner
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(3+tileAt(x, y).getRandom(),4));
				 	 }
					 //Lower
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(),2));
				 	 }
					 //Lower left corner
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(tileAt(x, y).getRandom(),4));
				 	 }
					 //Floating position
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(),1));
				 	 }
					 //  .
					 // o.
					 //  .
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(5,tileAt(x, y).getRandom()));
				 	 }
					 //.  
					 //.o
					 //.  
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(9,tileAt(x, y).getRandom()));
				 	 }
					 //
					 // o
					 //...
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(),0));
				 	 }
					 //...
					 // o
					 //
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(),2));
				 	 }
					 //
					 //.o.
					 //
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(),3));
				 	 }
					 // .
					 // o 
					 // .
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(),4));
				 	 }
					 //Middle block and undefined locations
					 else
					 {
						 tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(),1));
						 
						 //TODO Add inner corner textures
					 } 
				}
			}
		}
	
	public void updatePlayer(Player player){
		PhysicsBody body = (PhysicsBody)player; // Body is a class storing position, velocity, bounds and so on.

        int tileDimensions = 8;

        int leftTile = (int)body.getLeft() / tileDimensions;
        int topTile = (int)body.getTop() / tileDimensions;
        int rightTile = (int)Math.ceil((float)body.getRight() / tileDimensions - 1);
        int bottomTile = (int)Math.ceil(((float)body.getBottom() / tileDimensions) - 1);

        if (body.velocity.y > 0)
        {
            for (int x = leftTile; x <= rightTile; x++)
            {
                for (int y = bottomTile + 1; y <= (bottomTile + 1) + (body.velocity.y / tileDimensions); y++)
                {
                    if (tiles.get(x).get(y) != null && !(tiles.get(x).get(y).getType() > 0))
                    {
                        Vector2 newVelocity = new Vector2(body.velocity.x, MathUtils.clamp(body.velocity.y, 0F, (float)tiles.get(x).get(y).getType()));
                        body.velocity = newVelocity;
                        break;
                    }
                }
            }
        }
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public byte getType() {
		return type;
	}
}
