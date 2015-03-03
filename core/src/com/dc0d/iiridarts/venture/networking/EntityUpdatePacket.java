package com.dc0d.iiridarts.venture.networking;

import com.badlogic.gdx.math.Vector2;

public class EntityUpdatePacket extends UpdatePacket {
	public Vector2 pos;
	public Vector2 velocity;
    float stateTime;
    String id;
    boolean isDead;
	int health;
	boolean jump = false;
	boolean canFly = false;
	boolean hdir = false;
	byte vdir = 1;
	boolean run = false;
	boolean walk = false;
	public boolean isRemote;
	/**
	 * 
	 * @param pos Entity position
	 * @param velocity
	 * @param stateTime animation information
	 * @param id Alphanumeric entity id
	 * @param health Health for living entities
	 * @param isDead Does entity need to be discarded?
	 * @param hdir Is entity facing left or right?
	 * @param vdir Is entity crouching, standing, or jumping?
	 * @param walk Is entity is walking?
	 * @param run Is entity is running?
	 */
	public EntityUpdatePacket( 
			Vector2 pos,
			Vector2 velocity,
			float stateTime,
			String id,
			int health,
			boolean isDead,
			boolean hdir,
			byte vdir,
			boolean walk,
			boolean run
			){
		this.pos = pos;
		this.velocity = velocity;
		this.stateTime = stateTime;
		this.id = id;
	}
}
