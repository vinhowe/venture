package com.dc0d.thoriumlabs.venture;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
	                	//TODO Figure out how to draw tiles within view
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
	 //TODO Add a constant for world dimensions
	 //TODO Replace 1000 with world dimension constant
		 for(int x = 0; x < (int)Constants.mediumMapDimesions.x; x++){
			 tiles.add(x, new ArrayList<Tile>());
			 for(int y = 0; y < (int)Constants.mediumMapDimesions.y; y++){
				 tiles.get(x).add(y, new Tile((short)1,(byte)0,(byte)new Random().nextInt(5),(byte)new Random().nextInt(5)));
			 }
		 }
	}
	 
	public void update(int startX, int startY, int width, int height){
		for(int x = startX/8; x < width/8; x++){
			 for(int y = startX/8; y < width/8; y++){
				 tiles.get(x).get(y).setTexX((byte)new Random().nextInt(5));
				 tiles.get(x).get(y).setTexY((byte)new Random().nextInt(5));
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
							 tileAt(x, y).setTexCoords(new Vector2(0,1));
				 	 } 
					 //Upper left corner
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(0,3));
				 	 } 
					 //Upper
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(2,0));
				 	 }
					 //Upper right corner
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()<1&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(1,3));
				 	 }
					 //Right
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()>0)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(4,0));
				 	 } 
					 //Lower right corner
					 else if(tileAt(x+1,y).getType()<1&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(1,4));
				 	 }
					 //Lower
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()>0&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(1,2));
				 	 }
					 //Lower left corner
					 else if(tileAt(x+1,y).getType()>0&&
							 tileAt(x-1,y).getType()<1&&
							 tileAt(x,y+1).getType()>0&&
							 tileAt(x,y-1).getType()<1)
					 {
							 tileAt(x, y).setTexCoords(new Vector2(0,4));
				 	 }
					 //Middle block and undefined locations
					 else
					 {
						 tileAt(x, y).setTexCoords(new Vector2(1,1));
						 //TODO Set weighted chance of textures
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
