/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture.tiles;

import com.badlogic.gdx.math.Vector2;

/**
 * Construct for holding world tile state information
 * @author Thomas
 *
 */
public class Tile {
	private short type;
	private byte wallType;
	private byte byte1;
	private byte byte2;
	private byte byte3;
	private byte texX;
	private byte texY;
	
	/**
	 * Constructor for Tile
	 * @param type
	 * @param wallType
	 * @param texCoords
	 */
	
	public Tile(short type, byte wallType, Vector2 texCoords) {
		this.setType(type);
		this.setWallType(wallType);
		this.setTexX((byte) texCoords.x);
		this.setTexY((byte) texCoords.y);
		byte1 = 0;
		byte2 = 0;
		byte3 = 0;
		setRandom((byte)1);
	}
	
	/**
	 * Constructor for Tile
	 * @param type
	 * @param wallType
	 * @param texX
	 * @param texeY
	 */
	
	public Tile(short type, byte wallType, byte texX, byte texY) {
		this.setType(type);
		this.setWallType(wallType);
		this.setTexX(texX);
		this.setTexY(texY);
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public byte getWallType() {
		return wallType;
	}

	public void setWallType(byte wallType) {
		this.wallType = wallType;
	}

	public byte getByte1() {
		return byte1;
	}

	public void setByte1(byte byte1) {
		this.byte1 = byte1;
	}

	public byte getByte2() {
		return byte2;
	}

	public void setByte2(byte byte2) {
		this.byte2 = byte2;
	}

	public byte getByte3() {
		return byte3;
	}

	public void setByte3(byte byte3) {
		this.byte3 = byte3;
	}

	public byte getTexX() {
		return texX;
	}

	public void setTexX(byte texX) {
		this.texX = texX;
	}

	public byte getTexY() {
		return texY;
	}

	public void setTexY(byte texY) {
		this.texY = texY;
	}
	
	public void setTexCoords(Vector2 texCoords) {
		this.texY = (byte) texCoords.y;
		this.texX = (byte) texCoords.x;
	}
	
	public Vector2 getTexCoords() {
		return new Vector2(this.texX, this.texY);
	}
	
	public int getRandom(){
		return ((byte)((byte1 & 0xFF)&3));
	}
	
	public void setRandom(int rand){
		byte1 = (byte) (byte1 & ~(0x3));
		byte1 = (byte) (byte1 | rand);
	}
	
	public boolean isSolid() {
		return type > 0;
	}
	
}
