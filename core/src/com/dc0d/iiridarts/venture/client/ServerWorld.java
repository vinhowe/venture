/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */
package com.dc0d.iiridarts.venture.client;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.client.handlers.Utilities;
import com.dc0d.iiridarts.venture.client.tiles.Tile;

/**
 * The World class contains world information including tiles and entities.
 * 
 * @author Thomas Howe
 *
 */
public class ServerWorld {

	ArrayList<ArrayList<Tile>> tiles;
	private String name;
	private final byte type;
	VentureServer venture;

	public ServerWorld(String name, byte type, VentureServer venture) {
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
				tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2), true);
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
				tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2), true);
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
				tiles.get(x).get(y).setRandom(Utilities.randInt(0, 2), true);
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
