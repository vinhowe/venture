/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client.tiles;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.client.networking.NetworkArray;

/**
 * Construct for holding world tile state information
 * @author Thomas
 *
 */
public class Tile {
	private short type;
	
	private short wallType;
	
	private byte byte1;
	private byte byte2;
	private byte byte3;
	
	private byte texX;
	private byte texY;
	
	private boolean[] networkValueUpdates;
	
	private NetworkArray networkArray;
	
	//TODO Use a NetworkArray here instead of primitive arrays
	
	/**
	 * Constructor for Tile
	 * @param type
	 * @param wallType
	 * @param texCoords
	 */
	
	public Tile(short type, byte wallType, Vector2 texCoords) {
		this.type = type;
		this.wallType = wallType;
		this.texX = (byte) texCoords.x;
		this.texY = (byte) texCoords.y;
		byte1 = 0;
		byte2 = 0;
		byte3 = 0;
		setRandom(new Random().nextInt(2), false);
		networkArray = new NetworkArray();
		//networkArray.addVariable();
		//FIXME Add tile variables to networkArray
		networkValueUpdates = new boolean[8];
	}
	
	public Tile(short type, byte wallType, byte texX, byte texY) {
		this(type, wallType, new Vector2(texX, texY));
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
		//update = true;
	}

	public short getWallType() {
		return wallType;
	}

	public void setWallType(byte wallType) {
		this.wallType = wallType;
		//update = true;
	}

	public byte getByte1() {
		return byte1;
	}

	public void setByte1(byte byte1) {
		this.byte1 = byte1;
		//update = true;
	}

	public byte getByte2() {
		return byte2;
	}

	public void setByte2(byte byte2) {
		this.byte2 = byte2;
		//update = true;
	}

	public byte getByte3() {
		return byte3;
	}

	public void setByte3(byte byte3) {
		this.byte3 = byte3;
		//update = true;
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
	
	public void setRandom(int rand, boolean update){
		byte1 = (byte) (byte1 & ~(0x3));
		byte1 = (byte) (byte1 | rand);
		//this.update = update;
	}
	
	public void setBrightness(byte color, boolean update){
		byte1 = (byte) (color & ~(0x7 << 2));
		byte1 = (byte) (color | (color % 7) << 2);
		//this.update = update;
	}
	
	public int getBrightness(){
		return ((byte)((byte1 & 0xFF)&28)>>2);
	}
	
	public boolean isSolid() {
		return type > 0;
	}
}
