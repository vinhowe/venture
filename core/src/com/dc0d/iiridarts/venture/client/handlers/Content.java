/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dc0d.iiridarts.venture.client.Constants;

/**
 * Contains HashMaps for venture resources.
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
	private ArrayList<Texture> tileTextures;
	private ArrayList<Texture> tileShapeTextures;
	private ArrayList<Texture> itemTextures;
	private HashMap<Integer, Texture> itemGlowtextures;
	private HashMap<String, Music> music;
	private HashMap<String, Sound> sounds;
	private Random rand = new Random();
	
	public Content() {
		textures = new HashMap<String, Texture>();
		randtextures = new HashMap<String, ArrayList<TextureRegion>>();
		tileTextures = new ArrayList<Texture>();
		tileShapeTextures = new ArrayList<Texture>();
		itemTextures = new ArrayList<Texture>();
		music = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
		
		itemGlowtextures = new HashMap<Integer, Texture>();
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
	 * Loads all item textures from tiles directory into com.badlogic.gdx.graphics.Texture hashmap
	 */
	
	public void loadItemTextures() {
		for (int i = 1; i <= Constants.ITEMS; i++){
		Texture tex = new Texture(Gdx.files.internal(Constants.ITEMDIR+"/item_"+i+".png"));
		itemTextures.add(tex);
		}
	}
	
	/**
	 * Returns item texture from texture hashmap
	 * @param id
	 * @return
	 */
	
	public Texture getItemTexture(int id) {
		return itemTextures.get(id-1);
	}
	
	/**
	 * Loads all tile textures from tiles directory into com.badlogic.gdx.graphics.Texture hashmap
	 */
	
	public void loadTileTextures() {
		for (int i = 1; i <= Constants.TILETYPES; i++){
		Texture tex = new Texture(Gdx.files.internal(Constants.TILEDIR+"/tile_"+i+".png"));
		tileTextures.add(tex);
		}
	}

	public void loadTileShapeTextures() {
		for (int i = 1; i <= Constants.TILETYPES; i++){
			Texture tex = new Texture(Gdx.files.internal(Constants.TILEDIR+"/tile_"+i+"_shape.png"));
			tileShapeTextures.add(tex);
		}
	}
	
	/**
	 * Returns tile texture from texture hashmap
	 * @param id
	 * @return
	 */
	
	public Texture getTileTexture(int id) {
		return tileTextures.get(id-1);
	}

	/**
	 * Returns tile texture from texture hashmap
	 * @param id
	 * @return
	 */

	public Texture getTileShapeTexture(int id) {
		return tileShapeTextures.get(id-1);
	}

	/**
	 * Loads all item glow textures from tiles directory into com.badlogic.gdx.graphics.Texture hashmap
	 */
	
	public void loadItemGlowTextures() {
		for (int i = 1; i <= Constants.ITEMS; i++){
			if(Gdx.files.internal(Constants.ITEMDIR+"/item_"+i+"-glow.png").exists()){
				System.out.println("Found Glow Texture for "+i);
				Texture tex = new Texture(Gdx.files.internal(Constants.ITEMDIR+"/item_"+i+"-glow.png"));
				itemGlowtextures.put(i, tex);
			}
		}
	}
	
	/**
	 * Returns item glow texture from texture hashmap
	 * @param id
	 * @return
	 */
	
	public Texture getItemGlowTexture(int id) {
		System.out.println(itemGlowtextures.get(id));
		return itemGlowtextures.get(id);
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
