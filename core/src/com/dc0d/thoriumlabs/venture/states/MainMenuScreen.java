package com.dc0d.thoriumlabs.venture.states;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.dc0d.thoriumlabs.venture.Game;

public class MainMenuScreen implements Screen {
	
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
	
	//final Game game;
	
	OrthographicCamera camera;
	
	public MainMenuScreen(/*final Game game*/){
		//game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
	}
	@Override
	public void render(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
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

}
