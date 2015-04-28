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

import bloom.Bloom;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dc0d.iiridarts.venture.client.handlers.Content;
import com.dc0d.iiridarts.venture.client.networking.KryoNetClient;
import com.dc0d.iiridarts.venture.client.networking.EntityKey;

public class Venture extends com.badlogic.gdx.Game implements ApplicationListener {
	
	public Player player;
	public HashMap<String, EntityLiving> entities;
	public HashMap<String, Player> players;
    private SpriteBatch batch;
    private SpriteBatch glowBatch;
    @SuppressWarnings("unused")
    private SpriteBatch bgbatch;
    private SpriteBatch guiBatch; //FIXME GUI Batch initialization
    private ArrayList<World> world;
    private Viewport viewport;
    private TextureRegion[] bg;
    public OrthographicCamera camera;
	public OrthographicCamera scamera;
	private float zoom;
	public ArrayList<Sprite> tileSprites;
	public ArrayList<Sprite> entitySprites;
	public ArrayList<Sprite> itemSprites;
	public ArrayList<Sprite> itemGlowSprites;
	public Content res;
    boolean movingx;
    boolean movingy;
    boolean directiony;
    boolean directionx;
    FPSLogger fps;
    boolean oddFrame;
	public ArrayList<EntityKey> objects; //FIXME Work on EntityKey ArrayList
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
	double totalFrames;
	boolean physics;
	boolean loop;
	boolean sloMo;
	KryoNetClient networker;
	boolean gravity;
	int playerFrame;
	int sloMoCounter;
	boolean shift = false;
	KryoNetClient networkHandler;
	boolean local = false;
	Bloom bloom;
	MotionBlur blurShader;
    String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;
    Vector2 lastCameraCoords;
    Settings textureSettings;
    int cameraBob;
    int cameraBobPace;
    boolean cameraBobDirection;
    boolean enableShaders;
    //TODO Set up backgrounds
    
	@Override
	public void create() {
		init((byte) 1);
	}
	
	/**
	 * Initializes objects for
	 * @param type specifies the hardness of the initialization 
	 */
	public void init(byte type) {
		switch(type){
		case 1: // Shaders, Camera - environmental information and variables - used in starting application
			fps = new FPSLogger();
	    	touchPos = new Vector2(0,0);
	    	Gdx.input.setInputProcessor(new GameInput());
	    	camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			loadShaders();
			// Network Communication initialization
	    	networker = new KryoNetClient();
	    	totalFrames = 0;
	    	textureSettings = new Settings();
	    	textureSettings.filterMag = TextureFilter.MipMap;
	    	textureSettings.filterMin = TextureFilter.MipMap;
	    	enableShaders = false;
		case 2: //Position, Textures, Sprites - Updated information - hard reset
			// Textures
			res = new Content();
			res.loadTileTextures();
			res.loadItemTextures();
			res.loadItemGlowTextures();
			res.loadTexture("assets/images/backgrounds/bg.png");
			res.loadTexture("assets/images/entities/entity_2.png");
			res.loadTexture("assets/images/cursor.png");
			// MotionBlur Shader background colors
			//bloom.setClearColor(Constants.daySkyColor.r, Constants.daySkyColor.g, Constants.daySkyColor.b, 0);
	    	//bloom2.setClearColor(Constants.daySkyColor.r, Constants.daySkyColor.g, Constants.daySkyColor.b, 0);
	    	// Sprite Arrays - used in rendering loops
			tileSprites = new ArrayList<Sprite>();
			entitySprites = new ArrayList<Sprite>();
			lastCameraCoords = new Vector2();
			cameraBob = 0;
			cameraBobPace = 8;
			cameraBobDirection = false;
		case 3: //Switches - volatile information - soft reset
	        physics = true;
	        rightclick = false;
	        debug = true;
	        touch = false;
		break;
		}
		itemSprites = new ArrayList<Sprite>();
		itemGlowSprites = new ArrayList<Sprite>();
		bg = new TextureRegion[]{new TextureRegion(res.getTexture("bg"),80,50), new TextureRegion(res.getTexture("bg"),0,51,80,50),new TextureRegion(res.getTexture("bg"),0,51*2,80,50)};
		background = new TiledDrawable[]{new TiledDrawable(bg[0]), new TiledDrawable(bg[1]), new TiledDrawable(bg[2])};
		world = new ArrayList<World>();
		world.add(new World("alpha", (byte)1, this));
        batch = new SpriteBatch();
        glowBatch = new SpriteBatch();
        bgbatch = new SpriteBatch();
        world.get(0).generate();
        oddFrame = true;
        camera.update();
        zoom = 1F;
        viewport = new ScreenViewport(camera);
        player = new Player(world.get(0), false, 400*Constants.TILESIZE, 501*Constants.TILESIZE, true);
        logger = Logger.getLogger("Venture");
        handler = new ConsoleHandler();
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
        gravity = true;
        players = new HashMap<String, Player>();
        players.put(player.name, player);
        sloMoCounter = 0;
        if(!local){
	        try {
				networker.initAndConnect("127.0.0.1", 5557, "bufolo37*");
			} catch (IOException e) {
				// TODO Make client do something if it messes up
				e.printStackTrace();
			}
        }
        playerFrame = 0;
        networkHandler = new KryoNetClient();
        Pixmap pm = new Pixmap(Gdx.files.internal("assets/images/cursor.png"));
        Gdx.input.setCursorImage(pm, 0, 0);
        pm.dispose();
        lastCameraCoords = new Vector2(camera.position.x, camera.position.y);
	}

	/**
	 * Initializes Shader Programs
	 */
	public void loadShaders() {
		if(enableShaders){
		Bloom.useAlphaChannelAsMask = true;
    	bloom = new Bloom((int)(Gdx.graphics.getWidth() / 1.5f), (int)(Gdx.graphics.getHeight() / 1.5f), true, false, true);
    	blurShader = new MotionBlur((int)(Gdx.graphics.getWidth() / 1f), (int)(Gdx.graphics.getHeight() / 1f), true, false, true);
        ShaderProgram.pedantic = false;
		vertexShader = Gdx.files.internal("assets/shaders/motionblur.vert").readString();
        fragmentShader = Gdx.files.internal("assets/shaders/motionblur.frag").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        shaderProgram.setUniformMatrix("u_projViewTrans", camera.combined);
        int timeLoc = shaderProgram.getUniformLocation("v_time");
        if (!shaderProgram.isCompiled()) {
			System.out.println(shaderProgram.getLog());
			Gdx.app.exit();
		} else {
			Gdx.app.log("motion shader compiled", shaderProgram.getClass().getSimpleName());
	    }
        shaderProgram.begin();
       // shaderProgram.setUniformf("v_time", 1, 1, 1, 1);
        shaderProgram.setUniformf("MColor", 1);
        shaderProgram.setUniformf("screenSize", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shaderProgram.end();
       // for(int i = 0; i < shaderProgram.getUniforms().length; i++){
       // 	System.out.println(shaderProgram.getUniforms()[i]);
       // }
    	//bloom = new MotionBlur(3000, 3000, true, false, true);
    	//bloom2 = new MotionBlur(3000, 3000, true, false, true);
		}
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
    	loadShaders();
    	//bloom2.setClearColor(Constants.daySkyColor.r, Constants.daySkyColor.g, Constants.daySkyColor.b, 0);
    	//bloom2.setClearColor(Constants.daySkyColor.r, Constants.daySkyColor.g, Constants.daySkyColor.b, 0);
	}

	@Override
	public void render() {
		totalFrames++;
		//TODO If FPS logging is needed, use getFramesPerSecond
		renderLogic(Gdx.graphics.getDeltaTime());
		if(loop){
		update(Gdx.graphics.getDeltaTime());
		}
        Gdx.gl.glClearColor(0.0f, 0.65f, 0.90f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//logger.finer("delta on totalFrames " + totalFrames + " : "+ Gdx.graphics.getDeltaTime());
        if(debug && touch){
        	player.modifyTileDamage((int)camera.unproject(new Vector3(touchPos,0)).x/Constants.TILESIZE,(int) camera.unproject(new Vector3(touchPos,0)).y/Constants.TILESIZE, -50);
        }
        bg3pos.x += Gdx.graphics.getDeltaTime() * 1.5;
        bg2pos.x += Gdx.graphics.getDeltaTime() * 1;
        bg1pos.x += Gdx.graphics.getDeltaTime() * 0.5;
		//camera.zoom = 1 - 0.05F;
		scamera.zoom = 0.07f;
		//camera.zoom = zoom;
		camera.update();
		//scamera.update();
		//bgbatch.setProjectionMatrix(scamera.combined);
		//bgbatch.begin();
		//background[0].draw(bgbatch, 0,0,10000,10000);
		//background[1].draw(bgbatch, 0,0,10000,10000);
		//background[2].draw(bgbatch, 0,0,10000,10000);
		//TODO Generate clouds and other floating object randomly using textures
		//bgbatch.end();
		//bloom.setClearColor(0.0f, 0.65f, 0.90f, 0);
    	//bloom2.setClearColor(0.0f, 0.65f, 0.90f, 0);
		Vector2 lastCameraPosABS = new Vector2(Math.abs(lastCameraCoords.x), Math.abs(lastCameraCoords.y));
		Vector2 cameraPosDiff = new Vector2(Math.abs(camera.position.x), Math.abs(camera.position.y)).sub(lastCameraPosABS);
		cameraPosDiff = new Vector2(Math.abs(cameraPosDiff.x), Math.abs(cameraPosDiff.y));
		float cameraDiffMax = Math.max(cameraPosDiff.x, cameraPosDiff.y);
		//blurShader.setSize(Gdx.graphics.getWidth()*5, Gdx.graphics.getHeight()*5);
		batch.setProjectionMatrix(camera.combined);
		if(enableShaders) {
			blurShader.setBlurDirection(zoom, zoom);
			blurShader.setClearColor(0.0f, 0.65f, 0.90f, 1f);
			//System.out.println(10-zoom);
			shaderProgram.begin();
			shaderProgram.setUniformf("v_time", (cameraDiffMax/10)%10, (cameraDiffMax/10)%10, (cameraDiffMax/10)%10, (cameraDiffMax/10)%10);
			shaderProgram.setUniformf("grayScaleScale", Math.min(zoom/5, 1));
			shaderProgram.end();
			batch.setShader(shaderProgram);
			blurShader.capture();
		} else {
			batch.setShader(null);
		}
        batch.begin();
        
        for(int i = 0; i < tileSprites.size(); i++){
    		tileSprites.get(i).draw(batch);
        }
        
        for(int i = 0; i < itemSprites.size(); i++){
        	itemSprites.get(i).draw(batch);
        }
        
        
        batch.end();
        if(enableShaders) {
        blurShader.render();
        }
        
        batch.begin();
        
        for(int i = 0; i < entitySprites.size(); i++){
    		entitySprites.get(i).draw(batch);
        }
        
        batch.end();
        
        if(enableShaders) {
        
		bloom.capture();
		glowBatch.setProjectionMatrix(camera.combined);
		glowBatch.begin();
        
        
        for(int i = 0; i < itemSprites.size(); i++){
        	itemSprites.get(i).draw(glowBatch);
        }
		
        for(int i = 0; i < itemGlowSprites.size(); i++){
    		itemGlowSprites.get(i).draw(glowBatch);
        }
        
        glowBatch.end();
        bloom.render();
        }
        lastCameraCoords = new Vector2(camera.position.x, camera.position.y);
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
		
		
		player.updateLivingEntity(0.25f);
		player.sprite.setPosition(player.getPosition().x, player.getPosition().y);
		camera.position.x = MathUtils.clamp(player.position.x,Constants.WORLDEDGEMARGIN+camera.viewportWidth/2, ((Constants.mediumMapDimesions.x*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN)-(camera.viewportWidth/2));
		camera.position.y = MathUtils.clamp(player.position.y,camera.viewportHeight/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-Constants.WORLDEDGEMARGIN-(camera.viewportHeight/2));
		camera.position.y -= cameraBob/20;
		scamera.position.x = MathUtils.clamp(player.position.x,camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*Constants.TILESIZE)-(camera.viewportWidth/2)-Constants.WORLDEDGEMARGIN);
		scamera.position.y = MathUtils.clamp(player.position.y,camera.viewportHeight/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*Constants.TILESIZE)-(camera.viewportHeight/2)-Constants.WORLDEDGEMARGIN);
		
		//players.
		//sloMo = sloMoMax;
		//} else {
		//	sloMo--;
		//}
    }

	public void renderLogic(float delta){
		
		if(cameraBob >= 40 || cameraBob <= 0) {
			MathUtils.clamp(cameraBob, 5, 35);
			cameraBobDirection = !cameraBobDirection;
		}
		
		if(movingx){
			if(shift){
				cameraBob = (cameraBobDirection ? cameraBob + (cameraBobPace*2) : cameraBob - (cameraBobPace*2));
			} else {
				cameraBob = (cameraBobDirection ? cameraBob + cameraBobPace : cameraBob - cameraBobPace);
			}
		}
		
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
	        			TextureRegion tileRegion = new TextureRegion(res.getTileTexture(world.get(0).tileAt(x,y).getType()),world.get(0).tileTexX(x, y)*9,world.get(0).tileTexY(x, y)*9,8,8);
	        			tileRegion.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		        		Sprite sprite = new Sprite(tileRegion);
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
 
            //Zoom out
			if (amount > 0 && zoom < 100) {
				zoom += 0.5f;
			}
 
            //Zoom in
			if (amount < 0 && zoom > 0.1) {
				zoom -= 0.5f;
			}
 
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
	        switch (keycode)
	        {
	        case Keys.SPACE:
	            movingy = true;
	            directiony = true;
	            player.jumping = true;
	            break;
	        case Keys.D:
	        	movingx = true;
	        	directionx = true;
	        	player.walk = true;
	            break;
	        case Keys.S:
	        	movingy = true;
	        	directiony = false;
	        	player.walk = true;
	            break;
		    case Keys.A:
		    	movingx = true;
		    	directionx = false;
		    	player.walk = true;
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
		    case Keys.F5:
		    	loadShaders();
		    	break;
		    case Keys.F6:
		    	enableShaders = !enableShaders;
		    	loadShaders();
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
		  //  case Keys.ESCAPE:
		  //  	Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		  //  	break;
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
		            player.walk = false;
		            break;
		        case Keys.D:
		        	movingx = false;
		        	directionx = false;
		        	player.walk = false;
		            break;
		        case Keys.S:
		        	movingy = false;
		        	directiony = false;
		        	player.walk = false;
		            break;
			    case Keys.A:
			    	movingx = false;
			    	directionx = true;
			    	player.walk = false;
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