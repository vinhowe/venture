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
	private ArrayList<ArrayList<Sprite>> sprites;
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
		fps = new FPSLogger();
		sprites = new ArrayList<ArrayList<Sprite>>();
		res = new Content();
		res.loadTileTextures();
		res.loadTexture("assets/images/backgrounds/bg.png");
		bg = new TextureRegion[]{new TextureRegion(res.getTexture("bg"),80,50), new TextureRegion(res.getTexture("bg"),0,51,80,50),new TextureRegion(res.getTexture("bg"),0,51*2,80,50)};
		background = new TiledDrawable[]{new TiledDrawable(bg[0]), new TiledDrawable(bg[1]), new TiledDrawable(bg[2])};
		world = new World("alpha", (byte)1);
        batch = new SpriteBatch();
        bgbatch = new SpriteBatch();
        world.generate();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        viewport = new ScreenViewport(camera);
        Gdx.input.setInputProcessor(new GameInput());
	}

	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
		fps.log();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(touch){
        	world.tileAt((int)camera.unproject(new Vector3(touchPos,0)).x/16,(int) camera.unproject(new Vector3(touchPos,0)).y/16).setType((short)((rightclick == true) ? 0 : 1));
        }
        if(movingx){
            if(directionx)
            {
            	camera.position.x -= Gdx.graphics.getDeltaTime() * 100*8;
            	bg1pos.x += Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.x += Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.x += Gdx.graphics.getDeltaTime() * 10;
        	}
            else
            {
            	camera.position.x += Gdx.graphics.getDeltaTime() * 100*8;
            	bg1pos.x -= Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.x -= Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.x -= Gdx.graphics.getDeltaTime() * 10;
            }
        }
        if(movingy){
            if (directiony)
            {
            	camera.position.y += Gdx.graphics.getDeltaTime() * 100*8;
            	bg1pos.y -= Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.y -= Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.y -= Gdx.graphics.getDeltaTime() * 10;
        	}
            else
            {
            	camera.position.y = Math.max(0, Math.min(500, camera.position.y - Gdx.graphics.getDeltaTime() * 100*8));
            	bg1pos.y += Gdx.graphics.getDeltaTime() * 5;
            	bg2pos.y += Gdx.graphics.getDeltaTime() * 7.5;
            	bg3pos.y += Gdx.graphics.getDeltaTime() * 10;
            }
    	}
        bg3pos.x += Gdx.graphics.getDeltaTime() * 1.5;
        bg2pos.x += Gdx.graphics.getDeltaTime() * 1;
        bg1pos.x += Gdx.graphics.getDeltaTime() * 0.5;
		camera.zoom = 1 - 0.05F;
		scamera.zoom = 0.07f;
		camera.update();
		scamera.update();
		//System.out.println(camera.position.x + ":" + camera.position.y);
		bgbatch.setProjectionMatrix(scamera.combined);
		bgbatch.begin();
		//bgbatch.draw(bg,-scamera.viewportWidth/2,-scamera.viewportHeight/2,scamera.viewportWidth,scamera.viewportHeight);
		background[0].draw(bgbatch, 0+bg1pos.x,0+bg1pos.y,10000,10000);
		background[1].draw(bgbatch, 0+bg2pos.x,0+bg2pos.y,10000,10000);
		background[2].draw(bgbatch, 0+bg3pos.x,0+bg3pos.y,10000,10000);
		//.setWrap(TextureWrap.Repeat, TextureWrap.Repeat)
		bgbatch.end();
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int x = 0; x < sprites.size(); x++){
        	for(int y = 0; y < sprites.get(x).size(); y++){
        		sprites.get(x).get(y).draw(batch);
        	}
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
		sprites = new ArrayList<ArrayList<Sprite>>(500);
		
        for(int x = 0; x < camera.viewportWidth/16; x++){
        	if(x>=0){
        	sprites.add(x/16,new ArrayList<Sprite>());
		        for(int y = (int)(camera.unproject(new Vector3(0,camera.viewportHeight,0)).y)/16; y < world.tiles.get(x/16).size(); y++){
		        	if(!(x*16 > (camera.position.x+(camera.viewportWidth/2))+16||x*16<camera.position.x-(camera.viewportWidth/2)-16)
	        			&&!(y*16 > (camera.position.y+(camera.viewportHeight/2)+16)||y*16<camera.position.y-(camera.viewportHeight/2)-16)){
		        		if(world.tileAt(x, y).getType()>0){
			        	world.updateTile(x,y);
		        		Sprite sprite = new Sprite(new TextureRegion(res.getTileTexture(world.tileAt(x,y).getType()),world.tileTexX(x, y)*9,world.tileTexY(x, y)*9,8,8));
		        		sprite.setPosition(x*16,y*16);
		        		sprite.setScale(2.05F);
		        		sprites.get(x).add(sprite);
		        		//System.out.println(x+" "+y);
		        		}
		        	}
		        } 
	        }
            System.out.println(x);
        }
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
	        	directionx = false;
	            break;
	        case Keys.DOWN:
	        	movingy = true;
	        	directiony = false;
	            break;
		    case Keys.LEFT:
		    	movingx = true;
		    	directionx = true;
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