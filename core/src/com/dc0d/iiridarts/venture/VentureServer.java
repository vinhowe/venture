/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.handlers.Content;
import com.dc0d.iiridarts.venture.networking.ServerHandler;

public class VentureServer {
	
	public HashMap<String, EntityLiving> entities;
	public HashMap<String, Player> players;
    private ServerWorld world;
	Content res;
    boolean movingx;
    boolean movingy;
    boolean directiony;
    boolean directionx;
    FPSLogger fps;
    boolean oddFrame;
	//TODO Remove touchPos and touch variables. These exist for testing
	Vector2 touchPos;
	boolean touch;
	//TODO Turn off debug mode
	boolean rightclick;
	boolean debug;
	public Logger logger;
    ConsoleHandler handler;
	double frame;
	boolean physics;
	boolean loop;
	boolean sloMo;
	ServerHandler networker;
	boolean gravity;
	int playerFrame;
	
    
    //TODO Set up backgrounds
    
	public void init() {
		// Setting up Textures
		fps = new FPSLogger();
		frame = 0;
		res = new Content();
		res.loadTileTextures();
		res.loadTexture("assets/images/backgrounds/bg.png");
		res.loadTexture("assets/images/entities/entity_2.png");
		world = new ServerWorld("alpha", (byte)1, this);
        world.generate();
        oddFrame = true;
        logger = Logger.getLogger("Venture");
        handler = new ConsoleHandler();
        physics = true;
        rightclick = false;
        debug = true;
        touch = false;
        touchPos = new Vector2(0,0);
    	loop = true;
        if(debug){
        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        }
        sloMo = false;
        networker = new ServerHandler(this, world);
        gravity = true;
        players = new HashMap<String, Player>();
        entities = new HashMap<String, EntityLiving>();
    	try {
			networker.initServer(5557);
		} catch (IOException e) {
			// TODO Make dedicated server do crap if it messes up
			e.printStackTrace();
		}
        playerFrame = 0;
	}

	public static void main(String[] args) {
	}
	
	/**
	 * Runs venture logic, updates tile states, physics, lighting, input, entity logic, etc.
	 */
	
	public void update(float delta){
		if (playerFrame == 5) {
			playerFrame = 0;
			for(String key : entities.keySet()){
				//entitySprites.add(players.get(key).sprite);
				if(players.get(key).isRemote){
					//players.get(key).remoteUpdatePlayer(entityUpdatePackets.get(players.get(key).id));
					players.get(key).sprite.setPosition(players.get(key).getPosition().x, players.get(key).getPosition().y);
				}
			}
		}
    }
}