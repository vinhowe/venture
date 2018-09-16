/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public final class Constants {
	public static final float GRAVITY_X = 0f;
	public static final float GRAVITY_Y = 11.5f;
	public static final double STICKY_THRESHOLD = .0004;
	
	public static final int TILETYPES = 2;
	public static final int ITEMS = 3;
	public static int TILE_SIZE = 16;
	public static final int WORLDEDGEMARGIN = 25* TILE_SIZE;
	public static final int NETWORKTIMEOUT = 5000;
	
	public static final String ASSETSDIR = "assets";
	public static final String IMAGEDIR = ASSETSDIR+"/images";
	public static final String TILEDIR = IMAGEDIR+"/tiles";
	public static final String ITEMDIR = IMAGEDIR+"/items";
	public static final String SHADERS_DIR = ASSETSDIR+"/shaders";
	
	public static final Vector2 mediumMapDimesions = new Vector2(800,800);
	public static final Vector2 chunkSize = new Vector2(128,128);

    public static boolean DEBUG = true;
	public static boolean SERVER = false;
	
	public static final Color daySkyColor = new Color(0.0f, 0.65f, 0.90f, 0.25f);;
}
