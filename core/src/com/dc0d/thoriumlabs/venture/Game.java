package com.dc0d.thoriumlabs.venture;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dc0d.thoriumlabs.venture.handlers.Content;

public class Game extends com.badlogic.gdx.Game implements ApplicationListener{
	
    private SpriteBatch batch;
    //TODO Work on Player Object.
    private World world;
    private SpriteBatch bgbatch;
    private Viewport viewport;
    private TextureRegion bg;
	private OrthographicCamera camera;
	private float zoom = 0.5F;
	private ArrayList<Sprite> sprites;
	Content res;
    boolean movingx;
    boolean movingy;
    boolean directiony;
    boolean directionx;
    
	@Override
	public void create() {
		// Setting up Textures
		sprites = new ArrayList<Sprite>();
		res = new Content();
		res.loadTileTextures();
		res.loadTexture("assets/images/backgrounds/bg.png");
		bg = new TextureRegion(res.getTexture("bg"),80,40);
		world = new World("alpha", (byte)1);
        batch = new SpriteBatch();
        bgbatch = new SpriteBatch();
        world.generate();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = 800;
        camera.position.y = 800;
        camera.update();
        viewport = new ScreenViewport(camera);
        Gdx.input.setInputProcessor(new GameInput());
	}

	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(movingx){
            if(directionx)
            {
            	camera.position.x -=5;
        	}
            else
            {
            	camera.position.x +=5;
            }
        }
        if(movingy){
            if (directiony)
            {
            	camera.position.y +=5;
        	}
            else
            {
            	camera.position.y -=5;
            }
    	}
		camera.zoom = zoom;
		camera.update();
		//System.out.println(camera.position.x + ":" + camera.position.y);
		bgbatch.begin();
		bgbatch.draw(bg,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
	
	public void update(){
		//world.update((int) (camera.position.x-(camera.viewportWidth/2)),(int) (camera.position.y-(camera.viewportHeight/2)),(int)camera.viewportWidth,(int) camera.viewportHeight);
		sprites.clear();
        for(int x = 0; x < world.tiles.size(); x++){
	        for(int y = 0; y < world.tiles.get(x).size(); y++){
	        	if(!(x*8 > (camera.position.x+(camera.viewportWidth/2))||x*8<camera.position.x-(camera.viewportWidth/2))
        			&&!(y*8 > (camera.position.y+(camera.viewportHeight/2))||y*8<camera.position.y-(camera.viewportHeight/2))){
	        		if(world.tileAt(x, y).getType()>0){
	        		Sprite sprite = new Sprite(new TextureRegion(res.getTileTexture(0),world.tileTexX(x, y)*9,world.tileTexY(x, y)*9,8,8));
	        		sprite.setPosition(x*8,y*8);
	        		sprites.add(sprite);
	        		world.updateTile(x,y);
	        		//System.out.println(x+" "+y);
	        		}
	        	}
	        } 
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
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}
 
	}

}