/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.handlers.Utilities;
import com.dc0d.iiridarts.venture.networking.NetworkObject;
import com.dc0d.iiridarts.venture.tiles.Tile;

/**
 * The World class contains world information including tiles and entities.
 * 
 * @author Thomas Howe
 *
 */
public class World {

	ArrayList<ArrayList<Tile>> tiles;
	private String name;
	private final byte type;
	public Venture venture;
	public ArrayList<Entity> entities;

	public World(String name, byte type, Venture venture) {
		this.venture = venture;
		this.setName(name);
		this.type = type;
		tiles = new ArrayList<ArrayList<Tile>>();
	}

	/**
	 * This method draws the tiles in the world
	 * 
	 * @param spriteBatch
	 */
	public void draw(SpriteBatch spriteBatch) {
		int startx = (int) Math.floor((32) / 16);
		int endx = (int) Math.ceil((1024 + 32) / 16);
		int starty = (int) Math.floor((32) / 16);
		int endy = (int) Math.ceil((768 + 32) / 16);

		for (int x = startx; x < endx; x += 1) {
			for (int y = starty; y < endy; y += 1) {
				Tile tile = this.tileAt(x, y);
				if (tile != null) {
					// TODO Remove this function or find out how to implement it
				}

			}
		}
	}

	public Tile tileAt(int x, int y) {
		if (x > 0 && y > 0 && x < (int) Constants.mediumMapDimesions.x
				&& y < (int) Constants.mediumMapDimesions.y) {
			return tiles.get(x).get(y);
		} else
			return new Tile((short) -1, (byte) 0, (byte) 0, (byte) 0);
	}

	public Tile tileAt(Vector2 pos) {
		if (pos.x > 0 && pos.y > 0
				&& pos.x < (int) Constants.mediumMapDimesions.x
				&& pos.y < (int) Constants.mediumMapDimesions.y) {
			return tiles.get((int) pos.x).get((int) pos.y);
		} else
			return new Tile((short) -1, (byte) 0, (byte) 0, (byte) 0);
	}

	public Vector2 tileTex(int x, int y) {
		return tileAt(x, y).getTexCoords();
	}

	public byte tileTexX(int x, int y) {
		return tileAt(x, y).getTexX();
	}

	public byte tileTexY(int x, int y) {
		return tileAt(x, y).getTexY();
	}

	public void generate() {
		for (int x = 0; x < (int) Constants.mediumMapDimesions.x; x++) {
			tiles.add(x, new ArrayList<Tile>());
			for (int y = 0; y < (int) Constants.mediumMapDimesions.y; y++) {
				tiles.get(x).add(
						y,
						new Tile((short) /* new Random().nextInt(2) */1,
								(byte) 0, (byte) new Random().nextInt(5),
								(byte) new Random().nextInt(5)));
				tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2));
				tiles.get(x).get(y);
			}
		}

		for (int x = 0; x < (int) Constants.mediumMapDimesions.x; x++) {
			for (int y = 500; y < Constants.mediumMapDimesions.y - 250; y++) {
				tiles.get(x).add(
						y,
						new Tile((short) /* new Random().nextInt(2) */0,
								(byte) 0, (byte) new Random().nextInt(5),
								(byte) new Random().nextInt(5)));
				tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2));
				tiles.get(x).get(y);
			}
		}

		for (int x = 0; x < (int) Constants.mediumMapDimesions.x; x++) {
			for (int y = 750; y < Constants.mediumMapDimesions.y; y++) {
				tiles.get(x).add(
						y,
						new Tile((short) /* new Random().nextInt(2) */0,
								(byte) 0, (byte) new Random().nextInt(5),
								(byte) new Random().nextInt(5)));
				tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2));
				tiles.get(x).get(y);
			}
		}
		updateAllTiles();
	}

	public void updateAllTiles() {
		for (int x = 0; x < Constants.mediumMapDimesions.x; x++) {
			for (int y = 0; y < Constants.mediumMapDimesions.y; y++) {
				updateTile(x, y);
			}
		}
	}

	public void updateTile(Vector2 pos) {
		updateTile((int) pos.x, (int) pos.y);
	}

	/**
	 * Updates tile at specified coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void updateTile(int x, int y) {

		// TODO Remove or implement debug variables in updateTile()
	int debugUpdatee1 = (int) (tileAt(x, y).getTexCoords().x + tileAt(x, y)
				.getTexCoords().y);
		int debugUpdatee2 = 0;

		float time1 = System.nanoTime();

		boolean top = tileAt(x, y + 1).getType() > 0;
		boolean bottom = tileAt(x, y - 1).getType() > 0;
		boolean left = tileAt(x - 1, y).getType() > 0;
		boolean right = tileAt(x + 1, y).getType() > 0;

		boolean topleft = tileAt(x - 1, y + 1).getType() > 0;
		boolean topright = tileAt(x + 1, y + 1).getType() > 0;
		boolean bottomleft = tileAt(x - 1, y - 1).getType() > 0;
		boolean bottomright = tileAt(x + 1, y - 1).getType() > 0;

		// x+ is right
		// y+ is up
		if (x > 0 && y > 0 && x < (int) Constants.mediumMapDimesions.x
				&& y < (int) Constants.mediumMapDimesions.y) {
			if (tileAt(x, y) == null) {
				return;
			} else {
				// o = operative tile
				// # = solid tile
				// . = empty tile/tile wall
				
				// ###
				// #o#
				// ###
				if (top && topright && topleft && bottom && bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 1));
				}
				
				// &#.
				// .o#
				// &#.
				else if (top && !topright && bottom && !bottomright && !left && right) {
					tileAt(x, y).setTexCoords(new Vector2(5, 8+tileAt(x, y).getRandom()));
				}
				
				// .#&
				// #o.
				// .#&
				else if (top && !topleft && bottom && !bottomleft && left && !right) {
					tileAt(x, y).setTexCoords(new Vector2(9, 8+tileAt(x, y).getRandom()));
				}
				
				// &#.
				// .o#
				// &##
				else if (top && !topright && bottom && bottomright && !left && right) {
					tileAt(x, y).setTexCoords(new Vector2(0, 11+tileAt(x, y).getRandom()));
				}
				
				// ##&
				// #o.
				// .#&
				else if (top && topleft && bottom && !bottomleft && left && !right) {
					tileAt(x, y).setTexCoords(new Vector2(4, 11+tileAt(x, y).getRandom()));
				}
				
				// .##
				// #o#
				// &.&
				else if (top && topright && !topleft && !bottom && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 13));
				}
				
				// &.&
				// #o#
				// ##.
				else if (!top && bottom && bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 11));
				}
				
				//*//*//
				
				// &##
				// .o#
				// &#.
				else if (top && topright && bottom && !bottomright && !left && right) {
					tileAt(x, y).setTexCoords(new Vector2(5, 11+tileAt(x, y).getRandom()));
				}
				
				// .#&
				// #o.
				// ##&
				else if (top && !topleft && bottom && bottomleft && left && !right) {
					tileAt(x, y).setTexCoords(new Vector2(9, 11+tileAt(x, y).getRandom()));
				}
				
				// ##.
				// #o#
				// &.&
				else if (top && !topright && topleft && !bottom && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 13));
				}
				
				// &.&
				// #o#
				// .##
				else if (!top && bottom && !bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 11));
				}
				
				//*//*//
				
				//TODO Do in-corners
				
				// ...
				// .o#
				// .#.
				else if (!top && !left && bottom && !bottomright && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(10+tileAt(x, y).getRandom(), 0));
				}
				
				// ...
				// #o.
				// .#.
				else if (!top && bottom && !bottomleft && left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(13 + tileAt(x, y).getRandom(), 0));
				}
				
				// .#.
				// .o#
				// ...
				else if (top && !topright && !bottom && !left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(10+tileAt(x, y).getRandom(), 1));
				}
				
				// .#.
				// #o.
				// ...
				else if (top && !topleft && !bottom && left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(13 + tileAt(x, y).getRandom(), 1));
				}
				
				//*//*//
				
				// .##
				// .o#
				// .##
				else if (top && !left && bottom && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(0, tileAt(x, y).getRandom()));
				}
				// ...
				// .o#
				// .##
				else if (!top && !left && bottom && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(tileAt(x, y).getRandom(), 3));
				}
				
				// &.&
				// #o#
				// .#.
				else if (!top && bottom && !bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 8));
				}
				// .#.
				// #o.
				// .#.
				else if (top && !topright && !topleft && bottom && !bottomleft && !bottomright && left && !right) {
					tileAt(x, y).setTexCoords(new Vector2(9, 8+tileAt(x, y).getRandom()));
				}
				
				// ...
				// #o#
				// ###
				else if (!top && bottom && left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(1 + tileAt(x, y).getRandom(), 0));
				}
				// ...
				// #o.
				// ##.
				else if (!top && bottom && left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(3 + tileAt(x, y).getRandom(), 3));
				}
				// ##.
				// #o.
				// ##.
				else if (top && bottom && left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(4, tileAt(x, y).getRandom()));
				}
				// ##.
				// #o.
				// ...
				else if (top && !bottom && left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(3 + tileAt(x, y).getRandom(), 4));
				}
				
				// .#.
				// #o#
				// &.&
				else if (top && !topright && !topleft && !bottom && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 10));
				}
				
				// ###
				// #o#
				// ...
				else if (top && !bottom && left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(1 + tileAt(x, y).getRandom(), 2));
				}
				// .##
				// .o#
				// ...
				else if (top && !bottom && !left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(tileAt(x, y).getRandom(), 4));
				}
				// ...
				// .o.
				// ...
				else if (!top && !bottom && !left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(6 + tileAt(x, y).getRandom(), 1));
				}
				// ..#
				// .o#
				// ..#
				else if (!top && !bottom && !left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(5, tileAt(x, y).getRandom()));
				}
				// #..
				// #o.
				// #..
				else if (!top && !bottom && left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(9, tileAt(x, y).getRandom()));
				}
				// ...
				// .o.
				// ###
				else if (!top && bottom && !left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(6 + tileAt(x, y).getRandom(), 0));
				}
				// ###
				// .o.
				// ...
				else if (top && !bottom && !left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(6 + tileAt(x, y).getRandom(), 2));
				}
				// ...
				// #o#
				// ...
				else if (!top && !bottom && left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(6 + tileAt(x, y).getRandom(), 3));
				}
				// .#.
				// .o.
				// .#.
				else if (top && bottom && !left && !right) {
					tileAt(x, y).setTexCoords(
							new Vector2(6 + tileAt(x, y).getRandom(), 4));
				}
				// .##
				// #o#
				// ###
				else if (top && topright && !topleft && bottom && bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(0, 5+tileAt(x, y).getRandom()));
				}
				// ##.
				// #o#
				// ###
				else if (top && !topright && topleft && bottom && bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(1+tileAt(x, y).getRandom(), 5));
				}
				// ###
				// #o#
				// ##.
				else if (top && topright && topleft && bottom && bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(
							new Vector2(4, 5+tileAt(x, y).getRandom()));
				}
				// ###
				// #o#
				// .##
				else if (top && topright && topleft && bottom && !bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 7));
				}
				// ##.
				// #o#
				// .##
				else if (top && !topright && topleft && bottom && !bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 6));
				}
				// .##
				// #o#
				// ##.
				else if (top && topright && !topleft && bottom && bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 9));
				}
				// .##
				// #o#
				// .##
				else if (top && topright && !topleft && bottom && !bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(5, 5+tileAt(x, y).getRandom()));
				}
				// .#.
				// #o#
				// ###
				else if (top && !topright && !topleft && bottom && bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 5));
				}
				// ##.
				// #o#
				// ##.
				else if (top && !topright && topleft && bottom && bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(9, 5+tileAt(x, y).getRandom()));
				}
				// ###
				// #o#
				// .#.
				else if (top && topright && topleft && bottom && !bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(6+tileAt(x, y).getRandom(), 7));
				}
				// .#.
				// #o#
				// .#.
				else if (top && !topright && !topleft && bottom && !bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 6));
				}
				// ##.
				// #o#
				// .#.
				else if (top && !topright && topleft && bottom && !bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(0, 8+tileAt(x, y).getRandom()));
				}
				// ##.
				// #o#
				// .#.
				else if (top && !topright && topleft && bottom && !bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1, 8+tileAt(x, y).getRandom()));
				}
				// .##
				// #o#
				// .#.
				else if (top && topright && !topleft && bottom && !bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 8));
				}
				// .#.
				// #o#
				// .##
				else if (top && !topright && !topleft && bottom && !bottomleft && bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(4, 8+tileAt(x, y).getRandom()));
				}
				// .#.
				// #o#
				// ##.
				else if (top && !topright && !topleft && bottom && bottomleft && !bottomright && left && right) {
					tileAt(x, y).setTexCoords(new Vector2(1+tileAt(x, y).getRandom(), 10));
				}
				//TODO Undefined locations default to pink
				else {
					tileAt(x, y).setTexCoords(
							new Vector2(1 + tileAt(x, y).getRandom(), 1));

				}
			}
		}
		float time3 = System.nanoTime();
		debugUpdatee2 = (int) (tileAt(x, y).getTexCoords().x + tileAt(x, y)
				.getTexCoords().y);
		if (!(debugUpdatee1 == debugUpdatee2)) {
			// System.out.println((time3 - time1) / 1000000.0f);
		}
	}

	boolean collideable(int x, int y) {
		switch (tileAt(x, y).getType()) {
		case 0:
			return false;

		case 1:
			return true;
		}
		return false;
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
