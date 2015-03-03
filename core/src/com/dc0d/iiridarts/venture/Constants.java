/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.iiridarts.venture;

import com.badlogic.gdx.math.Vector2;

public interface Constants {
	public static final float GRAVITY_X = 0f;
	public static final float GRAVITY_Y = -5.5f;
	public static final double STICKY_THRESHOLD = .0004;
	
	public static final int TILETYPES = 1;
	public static final int TILESIZE = 24;
	public static final int WORLDEDGEMARGIN = 25*TILESIZE;
	public static final int NETWORKTIMEOUT = 5000;
	
	public static final String ASSETSDIR = "assets";
	public static final String IMAGEDIR = ASSETSDIR+"/images";
	public static final String TILEDIR = IMAGEDIR+"/tiles";
	
	public static final Vector2 mediumMapDimesions = new Vector2(800,800);
	public static final Vector2 chunkSize = new Vector2(128,128);
	
	public static boolean DEBUG = true;
	public static boolean SERVER = false;
}