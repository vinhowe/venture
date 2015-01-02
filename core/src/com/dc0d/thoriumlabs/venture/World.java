package com.dc0d.thoriumlabs.venture;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dc0d.thoriumlabs.venture.tiles.Tile;

/**
 * This is a serializable "world" class
 * @author Thomas Howe
 *
 */
public class World implements java.io.Serializable {
	
	ArrayList<ArrayList<Tile>> tiles;
	
	/**
	 * FOOD doesn't need a serial version UID!
	 */
	private static final long serialVersionUID = 1L;
	
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
		 return tiles.get(x).get(y);
	 }
}
