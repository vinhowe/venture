/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture;

import java.util.ArrayList;

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
import com.dc0d.oxidearts.venture.handlers.Content;
import com.dc0d.oxidearts.venture.physics.PhysicsEngine;

public class Game extends com.badlogic.gdx.Game implements ApplicationListener{
	
	private Player player;
    private SpriteBatch batch;
    private World world;
    private SpriteBatch bgbatch;
    private Viewport viewport;
    private TextureRegion[] bg;
	private OrthographicCamera camera;
	private OrthographicCamera scamera;
	private float zoom = 1F;
	private ArrayList<Sprite> sprites;
	Content res;
    boolean movingx;
    boolean movingy;
    boolean directiony;
    boolean directionx;
    FPSLogger fps;
    boolean oddFrame = true;
    TiledDrawable[] background;
	Vector2 bg1pos = new Vector2(0,0);
	Vector2 bg2pos = new Vector2(0,0);
	Vector2 bg3pos = new Vector2(0,0);
	PhysicsEngine physicsEngine = new PhysicsEngine();
	//TODO Remove touchPos and touch variables. These exist for testing
	Vector2 touchPos = new Vector2(0,0);
	boolean touch = false;
	boolean rightclick = false;
    
    //TODO Set up backgrounds
    
	@Override
	public void create() {
		// Setting up Textures
		//fps = new FPSLogger();
		sprites = new ArrayList<Sprite>();
		res = new Content();
		res.loadTileTextures();
		res.loadTexture("assets/images/backgrounds/bg.png");
		res.loadTexture("assets/images/player/player_male.png");
		bg = new TextureRegion[]{new TextureRegion(res.getTexture("bg"),80,50), new TextureRegion(res.getTexture("bg"),0,51,80,50),new TextureRegion(res.getTexture("bg"),0,51*2,80,50)};
		background = new TiledDrawable[]{new TiledDrawable(bg[0]), new TiledDrawable(bg[1]), new TiledDrawable(bg[2])};
		world = new World("alpha", (byte)1, this);
        batch = new SpriteBatch();
        bgbatch = new SpriteBatch();
        world.generate();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = 400*16;
        camera.position.y = 500*16;
        camera.update();
        viewport = new ScreenViewport(camera);
        player = new Player(world);
        Gdx.input.setInputProcessor(new GameInput());
	}

	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.x*16)-(camera.viewportWidth/2)-Constants.WORLDEDGEMARGIN);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight/2+Constants.WORLDEDGEMARGIN, (Constants.mediumMapDimesions.y*16)-(camera.viewportHeight/2)-Constants.WORLDEDGEMARGIN);
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
		update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(touch){
        	world.tileAt((int)camera.unproject(new Vector3(touchPos,0)).x/16,(int) camera.unproject(new Vector3(touchPos,0)).y/16).setType((short)((rightclick == true) ? 0 : 1));
        }
        if(movingx){
            if(directionx)
            {
            	camera.position.x = Math.min(camera.position.x + Gdx.graphics.getDeltaTime() * 300*8, (Constants.mediumMapDimesions.x*16)-(camera.viewportWidth/2)-Constants.WORLDEDGEMARGIN);
            	bg1pos.x -= Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.x -= Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.x -= Gdx.graphics.getDeltaTime() * 10;
        	}
            else
            {
            	camera.position.x = Math.max(camera.position.x - Gdx.graphics.getDeltaTime() * 300*8, camera.viewportWidth/2+Constants.WORLDEDGEMARGIN);
            	bg1pos.x += Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.x += Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.x += Gdx.graphics.getDeltaTime() * 10;
            }
        }
        if(movingy){
            if (directiony)
            {
            	camera.position.y = Math.min(camera.position.y + Gdx.graphics.getDeltaTime() * 300*8, (Constants.mediumMapDimesions.y*16)-(camera.viewportHeight/2)-Constants.WORLDEDGEMARGIN);
            	bg1pos.y -= Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.y -= Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.y -= Gdx.graphics.getDeltaTime() * 10;
        	}
            else
            {
            	camera.position.y = Math.max(camera.position.y - Gdx.graphics.getDeltaTime() * 300*8, camera.viewportHeight/2+Constants.WORLDEDGEMARGIN);
            	bg1pos.y += Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.y += Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.y += Gdx.graphics.getDeltaTime() * 10;
            }
    	}
        bg3pos.x += Gdx.graphics.getDeltaTime() * 1.5;
        bg2pos.x += Gdx.graphics.getDeltaTime() * 1;
        bg1pos.x += Gdx.graphics.getDeltaTime() * 0.5;
		//camera.zoom = 1 - 0.05F;
		scamera.zoom = 0.07f;
		camera.update();
		scamera.update();
		bgbatch.setProjectionMatrix(scamera.combined);
		bgbatch.begin();
		background[0].draw(bgbatch, 0+bg1pos.x,0+bg1pos.y,10000,10000);
		background[1].draw(bgbatch, 0+bg2pos.x,0+bg2pos.y,10000,10000);
		background[2].draw(bgbatch, 0+bg3pos.x,0+bg3pos.y,10000,10000);
		//TODO Generate clouds and other floating object randomly using textures
		bgbatch.end();
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i = 0; i < sprites.size(); i++){
        		sprites.get(i).draw(batch);
        }
        batch.end();
	}
	
	/**
	 * Runs game logic, updates tile states, physics, lighting, input, entity logic, etc.
	 */
	
	public void update(float delta){
		//world.update((int) (camera.position.x-(camera.viewportWidth/2)),(int) (camera.position.y-(camera.viewportHeight/2)),(int)camera.viewportWidth,(int) camera.viewportHeight);
		if(!oddFrame){
			oddFrame = true;
			return;
		}
		oddFrame = false;
		sprites = new ArrayList<Sprite>(500);
		
		Vector2 startingTile = new Vector2((int)(camera.position.x-camera.viewportWidth)/16,
				(int)(camera.position.y-camera.viewportHeight)/16);
		
		Vector2 lastTile = new Vector2((int)startingTile.x+((camera.viewportWidth*2)/16),
				(int)startingTile.y+((camera.viewportHeight*2)/16));
		
		for(int x = (int) startingTile.x; x <= lastTile.x; x++){
			for(int y = (int) startingTile.y; y <= lastTile.y; y++)
		        		if(world.tileAt(x, y).getType()>0){
			        	world.updateTile(x,y);
		        		Sprite sprite = new Sprite(new TextureRegion(res.getTileTexture(world.tileAt(x,y).getType()),world.tileTexX(x, y)*9,world.tileTexY(x, y)*9,8,8));
		        		sprite.setPosition(x*16,y*16);
		        		sprite.setScale(2.0F);
		        		sprites.add(sprite);
		        		//System.out.println(x+" "+y);
		        		}
           // System.out.println(x);
		}
		world.updatePlayer(player, delta);
		player.sprite.setPosition(player.getPosition().x, player.getPosition().y);
		sprites.add(player.sprite);
		camera.position.x = player.position.x;
		camera.position.y = player.position.y;
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
			if (amount > 0 && zoom < 1) {
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
	        case Keys.UP:
	            movingy = true;
	            directiony = true;
	            break;
	        case Keys.RIGHT:
	        	movingx = true;
	        	directionx = true;
	            break;
	        case Keys.DOWN:
	        	movingy = true;
	        	directiony = false;
	            break;
		    case Keys.LEFT:
		    	movingx = true;
		    	directionx = false;
		        break;
		    }
	        return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			 switch (keycode)
		        {
		        case Keys.UP:
		            movingy = false;
		            directiony = true;
		            break;
		        case Keys.RIGHT:
		        	movingx = false;
		        	directionx = false;
		            break;
		        case Keys.DOWN:
		        	movingy = false;
		        	directiony = false;
		            break;
			    case Keys.LEFT:
			    	movingx = false;
			    	directionx = true;
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
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}
 
	}

}