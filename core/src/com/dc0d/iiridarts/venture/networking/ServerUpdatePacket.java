package com.dc0d.iiridarts.venture.networking;

import java.util.ArrayList;

public class ServerUpdatePacket extends UpdatePacket {
	ArrayList<TileUpdatePacket> tileUpdates = null;
	ArrayList<EntityUpdatePacket> entityUpdates = null;
	ArrayList<PlayerUpdatePacket> playerUpdates = null;
	ArrayList<PlayerJoinPacket> playerJoinUpdates = null;
	ArrayList<PlayerQuitPacket> playerQuitUpdates = null;
	
	public ServerUpdatePacket() {
		
	}
}
