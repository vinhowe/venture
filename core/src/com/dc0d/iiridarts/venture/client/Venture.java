/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dc0d.iiridarts.venture.client.handlers.Content;
import com.dc0d.iiridarts.venture.client.networking.ClientNetworkHandler;
import com.dc0d.iiridarts.venture.client.networking.NetworkObject;

public class Venture extends com.badlogic.gdx.Game implements ApplicationListener{
	
	public Player player;
	public HashMap<String, EntityLiving> entities;
	public HashMap<String, Player> players;
    private SpriteBatch batch;
    private ArrayList<World> world;
    @SuppressWarnings("unused")
    private SpriteBatch bgbatch;
    private Viewport viewport;
    private TextureRegion[] bg;
    public OrthographicCamera camera;
	public OrthographicCamera scamera;
	private float zoom;
	public ArrayList<Sprite> tileSprites;
	public ArrayList<Sprite> entitySprites;
	public ArrayList<Sprite> itemSprites;
	public Content res;
    boolean movingx;
    boolean movingy;
    boolean directiony;
    boolean directionx;
    FPSLogger fps;
    boolean oddFrame;
	public ArrayList<NetworkObject> objects; //FIXME Work on NetworkObject ArrayList
    TiledDrawable[] background;
	Vector2 bg1pos;
	Vector2 bg2pos;
	Vector2 bg3pos;
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
	ClientNetworkHandler networker;
	boolean gravity;
	int playerFrame;
	int sloMoCounter;
	boolean shift = false;
	ClientNetworkHandler networkHandler;
	
    //TODO Set up backgrounds
    
	@Override
	public void create() {
		// Setting up Textures
		Gdx.input.setCursorCatched(true);
		fps = new FPSLogger();
		frame = 0;
		tileSprites = new ArrayList<Sprite>();
		entitySprites = new ArrayList<Sprite>();
		itemSprites = new ArrayList<Sprite>();
		res = new Content();
		res.loadTileTextures();
		res.loadItemTextures();
		res.loadTexture("assets/images/backgrounds/bg.png");
		res.loadTexture("assets/images/entities/entity_2.png");
		bg = new TextureRegion[]{new TextureRegion(res.getTexture("bg"),80,50), new TextureRegion(res.getTexture("bg"),0,51,80,50),new TextureRegion(res.getTexture("bg"),0,51*2,80,50)};
		background = new TiledDrawable[]{new TiledDrawable(bg[0]), new TiledDrawable(bg[1]), new TiledDrawable(bg[2])};
		world = new ArrayList<World>();
		world.add(new World("alpha", (byte)1, this));
        batch = new SpriteBatch();
        bgbatch = new SpriteBatch();
        world.get(0).generate();
        oddFrame = true;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = 400*Constants.TILESIZE;
        camera.position.y = 500*Constants.TILESIZE;
        camera.update();
        zoom = 1F;
        viewport = new ScreenViewport(camera);
        player = new Player(world.get(0), false, 400*Constants.TILESIZE, 501*Constants.TILESIZE, true);
        Gdx.input.setInputProcessor(new GameInput());
        logger = Logger.getLogger("Venture");
        handler = new ConsoleHandler();
        physics = true;
        rightclick = false;
        debug = true;
        touch = false;
        touchPos = new Vector2(0,0);
    	bg1pos = new Vector2(0,0);
    	bg2pos = new Vector2(0,0);
    	bg3pos = new Vector2(0,0);
    	loop = true;
        if(debug){
        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        }
        sloMo = false;
        entitySprites.add(player.sprite);
        networker = new ClientNetworkHandler();
        gravity = true;
        players = new HashMap<String, Player>();
        players.put(player.name, player);
        sloMoCounter = 0;
        try {
			networker.initAndConnect("127.0.0.1", 5557, "bufolo37#");
		} catch (IOException e) {
			// TODO Make client do crap if it messes up
			e.printStackTrace();
		}
        playerFrame = 0;
        networkHandler = new ClientNetworkHandler();
	}

	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*Constants.TILESIZE)-(camera.viewportWidth/2)-Constants.WORLDEDGEMARGIN);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-(camera.viewportHeight/2)-Constants.WORLDEDGEMARGIN);
		//camera = new OrthographicCamera(width, height);
	    //camera.translate(width/2, height/2, 0);
	    scamera = new OrthographicCamera(width, height);
	    scamera.translate(width/2, height/2, 0);
	    //Gdx.graphics.setDisplayMode(width,height, false);
	    //TODO Remove console coordinate output and use debug tools :P
	    System.out.println("x:"+camera.viewportWidth+" y: "+camera.viewportHeight);
	    System.out.println("x:"+width+" y: "+height);
	    System.out.println("x:"+Gdx.graphics.getWidth()+" y: "+Gdx.graphics.getHeight());
	}

	@Override
	public void render() {
		//fps.log();
		renderLogic(Gdx.graphics.getDeltaTime());
		if(loop){
		update(Gdx.graphics.getDeltaTime());
		}
        Gdx.gl.glClearColor(0.0f, 0.65f, 0.90f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//logger.finer("delta on frame " + frame + " : "+ Gdx.graphics.getDeltaTime());
        if(debug && touch){
        	player.breakTile((int)camera.unproject(new Vector3(touchPos,0)).x/Constants.TILESIZE,(int) camera.unproject(new Vector3(touchPos,0)).y/Constants.TILESIZE);
        }
        bg3pos.x += Gdx.graphics.getDeltaTime() * 1.5;
        bg2pos.x += Gdx.graphics.getDeltaTime() * 1;
        bg1pos.x += Gdx.graphics.getDeltaTime() * 0.5;
		//camera.zoom = 1 - 0.05F;
		scamera.zoom = 0.07f;
		camera.zoom = zoom;
		camera.update();
		//scamera.update();
		//bgbatch.setProjectionMatrix(scamera.combined);
		//bgbatch.begin();
		//background[0].draw(bgbatch, 0,0,10000,10000);
		//background[1].draw(bgbatch, 0,0,10000,10000);
		//background[2].draw(bgbatch, 0,0,10000,10000);
		//TODO Generate clouds and other floating object randomly using textures
		//bgbatch.end();
		
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i = 0; i < tileSprites.size(); i++){
    		tileSprites.get(i).draw(batch);
        }
        for(int i = 0; i < entitySprites.size(); i++){
    		entitySprites.get(i).draw(batch);
        }
        for(int i = 0; i < itemSprites.size(); i++){
        	itemSprites.get(i).draw(batch);
        }
        batch.end();
	}
	
	/**
	 * Runs venture logic, updates tile states, physics, lighting, input, entity logic, etc.
	 */
	
	public void update(float delta){
		if(sloMo){
			if(!oddFrame){
				return;
			}
		}
		//I do this three times so that the logic is faster
		if (playerFrame == 5) {
			playerFrame = 0;
			entitySprites.clear();
			for(String key : entities.keySet()){
				entitySprites.add(players.get(key).sprite);
				if(players.get(key).isRemote){
					//players.get(key).remoteUpdatePlayer(entityUpdatePackets.get(players.get(key).id));
					players.get(key).sprite.setPosition(players.get(key).getPosition().x, players.get(key).getPosition().y);
				}
			}
		}
		if(oddFrame){
			//entitySprites.clear();
		}
		player.updateLivingEntity(0.25f);
		player.sprite.setPosition(player.getPosition().x, player.getPosition().y);
		camera.position.x = MathUtils.clamp(player.position.x,Constants.WORLDEDGEMARGIN+camera.viewportWidth/2, ((Constants.mediumMapDimesions.x*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN)-(camera.viewportWidth/2));
		camera.position.y = MathUtils.clamp(player.position.y,camera.viewportHeight/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN-(camera.viewportHeight/2));
		scamera.position.x = MathUtils.clamp(player.position.x,camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*Constants.TILESIZE)-(camera.viewportWidth/2)-Constants.WORLDEDGEMARGIN);
		scamera.position.y = MathUtils.clamp(player.position.y,camera.viewportHeight/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-(camera.viewportHeight/2)-Constants.WORLDEDGEMARGIN);
		
		//players.
		//sloMo = sloMoMax;
		//} else {
		//	sloMo--;
		//}
    }

	public void renderLogic(float delta){
		frame++;
		//world.update((int) (camera.position.x-(camera.viewportWidth/2)),(int) (camera.position.y-(camera.viewportHeight/2)),(int)camera.viewportWidth,(int) camera.viewportHeight);
		if(!oddFrame){
			oddFrame = true;
			return;
		}
		oddFrame = false;
		
		tileSprites.clear();
		
		Vector2 startingTile = new Vector2((int)(camera.position.x-camera.viewportWidth)/Constants.TILESIZE,
				(int)(camera.position.y-camera.viewportHeight)/Constants.TILESIZE);
		
		Vector2 lastTile = new Vector2((int)startingTile.x+((camera.viewportWidth*2)/Constants.TILESIZE),
				(int)startingTile.y+((camera.viewportHeight*2)/Constants.TILESIZE));
		
		for(int x = (int) startingTile.x; x <= lastTile.x; x++){
			for(int y = (int) startingTile.y; y <= lastTile.y; y++)
		        		if(world.get(0).tileAt(x, y).isSolid()){
	        			world.get(0).updateTile(x,y);
		        		Sprite sprite = new Sprite(new TextureRegion(res.getTileTexture(world.get(0).tileAt(x,y).getType()),world.get(0).tileTexX(x, y)*9,world.get(0).tileTexY(x, y)*9,8,8));
		        		sprite.setPosition(x*Constants.TILESIZE,y*Constants.TILESIZE);
		        		sprite.setSize(Constants.TILESIZE,Constants.TILESIZE);
		        		sprite.setScale(1.05f);
		        		tileSprites.add(sprite);
		        		//System.out.println(x+" "+y);
		        		}
           // System.out.println(x);
		}
		//player.sprite.setPosition(player.getPosition().x, player.getPosition().y);
		//for(int p = 0; p <= players.size(); p++) {
			//Player iplayer = players.get(p);
			//item.setPosition(player.position.x, player.position.y);
			//item.setScale(2f);
			//itemSprites.add(item);
		//}
	}
	
	
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

	class GameInput implements InputProcessor {
		 
		@Override
		public boolean scrolled(int amount) {
 
            /*//Zoom out
			if (amount > 0 && zoom < 2) {
				zoom += 0.1f;
			}
 
            //Zoom in
			if (amount < 0 && zoom > 0.1) {
				zoom -= 0.1f;
			}*/
 
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
	        switch (keycode)
	        {
	        case Keys.W:
	            movingy = true;
	            directiony = true;
	            player.jumping = true;
	            break;
	        case Keys.D:
	        	movingx = true;
	        	directionx = true;
	            break;
	        case Keys.S:
	        	movingy = true;
	        	directiony = false;
	            break;
		    case Keys.A:
		    	movingx = true;
		    	directionx = false;
		        break;
		    case Keys.SPACE:
		    	//player.jump(5f);
		        break;
		    case Keys.F8:
		    	create();
		    	break;
		    case Keys.F9:
		    	physics = !physics;
		    	break;
		    case Keys.F10:
		    	loop = !loop;
		    	break;
		    case Keys.F1:
		    	sloMo = !sloMo;
		    	break;
		    case Keys.F2:
		    	player.canFly = !player.canFly;
		    	break;
		    case Keys.F3:
		    	player.setPosition(400*Constants.TILESIZE, 501*Constants.TILESIZE);
		    	break;
		    case Keys.F4:
		    	gravity = !gravity;
		    	break;
		    case Keys.ESCAPE:
		    	Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		    	break;
		    case Keys.SHIFT_LEFT:
		    	shift = true;
		    	break;
	        }
	        return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			 switch (keycode)
		        {
		        case Keys.W:
		            movingy = false;
		            directiony = true;
		            break;
		        case Keys.D:
		        	movingx = false;
		        	directionx = false;
		            break;
		        case Keys.S:
		        	movingy = false;
		        	directiony = false;
		            break;
			    case Keys.A:
			    	movingx = false;
			    	directionx = true;
			        break;
			    case Keys.SHIFT_LEFT:
			    	shift = false;
			    	break;
			    }
	        return true;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			touchPos.set(screenX, screenY);
			touch = true;
			rightclick = (button == 1) ? true: false;
			player.swingingSword = true;
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			touch = false;
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			touchPos.set(screenX, screenY);
			touch = true;
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}
 
	}

}