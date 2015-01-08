/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January 2015
 */

package com.dc0d.oxidearts.venture.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dc0d.oxidearts.venture.Constants;

/**
 * Contains HashMaps for game resources.
 */
public class Content {
	
	/**
	 * Stores generic textures such as branding and overlays
	 */
	private HashMap<String, Texture> textures;
	/**
	 * @deprecated
	 * Stored textures to be used randomly. We now use individual arrays to for this
	 */
	private HashMap<String, ArrayList<TextureRegion>> randtextures;
	private ArrayList<Texture> tiletextures;
	private HashMap<String, Music> music;
	private HashMap<String, Sound> sounds;
	private Random rand = new Random();
	
	public Content() {
		textures = new HashMap<String, Texture>();
		randtextures = new HashMap<String, ArrayList<TextureRegion>>();
		tiletextures = new ArrayList<Texture>();
		music = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
	}
	
	/**
	 * Loads generic textures such as branding into a hashmap to be used later.
	 * @param path
	 */
	
	public void loadTexture(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadTexture(path, key);
	}
	
	/**
	 * Loads generic textures such as branding into a hashmap to be used later.
	 * @param path
	 * @param key
	 */
	
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key, tex);
	}
	
	/**
	 * Returns Texture from main Texture hashmap
	 * @param key
	 * @return
	 */
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	/**
	 * Removes Texture from main texture hashmap
	 * @param key
	 */
	
	public void removeTexture(String key) {
		Texture tex = textures.get(key);
		if(tex != null) {
			textures.remove(key);
			tex.dispose();
		}
	}
	
	/**
	 * Loads all tile textures from tiles directory into com.badlogic.gdx.graphics.Texture hashmap
	 */
	
	public void loadTileTextures() {
		for (int i = 1; i <= Constants.TILES; i++){
		Texture tex = new Texture(Gdx.files.internal(Constants.TILEDIR+"/tile_"+i+".png"));
		tiletextures.add(tex);
		}
	}
	
	/**
	 * Returns tile texture from texture hashmap
	 * @param id
	 * @return
	 */
	
	public Texture getTileTexture(int id) {
		return tiletextures.get(id-1);
	}
	
	/**
	 * @deprecated Previously used to add textures to a two dimensional array. We use individual handlers now.
	 * @param path
	 * @param numtextures
	 */
	
	public void loadRandTexture(String path, int numtextures) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadTexture(path, key, numtextures);
	}
	
	/**
	 * @deprecated Previously used to add textures to a two dimensional array. We use individual handlers now.
	 * @param path
	 * @param key
	 * @param numtextures
	 */
	
	public void loadTexture(String path, String key, int numtextures) {
		Texture tex = new Texture(Gdx.files.internal(path));
		ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();
		for(int i = 0; i < numtextures; i++){
			TextureRegion randtex = new TextureRegion(tex, i*8+2*(i+1),2,8,8);
			regions.add(i, randtex);
		}
		randtextures.put(key, regions);
	}
	
	/**
	 * @deprecated Was used to return random textures from an array. Textures are now handled individually. 
	 * @param key
	 * @return
	 */
	public TextureRegion getRandTexture(String key) {
		return randtextures.get(key).get(rand.nextInt(randtextures.get(key).size()));
	}
	
	/*********/
	/* Music */
	/*********/
	
	public void loadMusic(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadMusic(path, key);
	}
	public void loadMusic(String path, String key) {
		Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
		music.put(key, m);
	}
	public Music getMusic(String key) {
		return music.get(key);
	}
	public void removeMusic(String key) {
		Music m = music.get(key);
		if(m != null) {
			music.remove(key);
			m.dispose();
		}
	}
	
	/*******/
	/* SFX */
	/*******/
	
	public void loadSound(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadSound(path, key);
	}
	public void loadSound(String path, String key) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(key, sound);
	}
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	public void removeSound(String key) {
		Sound sound = sounds.get(key);
		if(sound != null) {
			sounds.remove(key);
			sound.dispose();
		}
	}
	
	/*********/
	/* other */
	/*********/
	
	public void removeAll() {
		/*Iterator<Map.Entry<String, Texture>> iter1 = textures.entrySet().iterator();
		while(iter1.hasNext()) {
			Texture tex = iter1.next().getValue();
			tex.dispose();
			iter1.remove();
		}
		Iterator<Map.Entry<String, Music>> iter2 = music.entrySet().iterator();
		while(iter2.hasNext()) {
			Music music = iter2.next().getValue();
			music.dispose();
			iter2.remove();
		}
		Iterator<Map.Entry<String, Sound>> iter3 = sounds.entrySet().iterator();
		while(iter3.hasNext()) {
			Sound sound = iter3.next().getValue();
			sound.dispose();
			iter3.remove();
		}*/
		for(Object o : textures.values()) {
			Texture tex = (Texture) o;
			tex.dispose();
		}
		textures.clear();
		for(Object o : music.values()) {
			Music music = (Music) o;
			music.dispose();
		}
		music.clear();
		for(Object o : sounds.values()) {
			Sound sound = (Sound) o;
			sound.dispose();
		}
		sounds.clear();
	}
	
}
