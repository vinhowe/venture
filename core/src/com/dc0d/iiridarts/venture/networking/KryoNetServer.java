package com.dc0d.iiridarts.venture.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.dc0d.iiridarts.venture.Player;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class KryoNetServer {
	
	Server server;
	NetworkHandler handler;
	
	public KryoNetServer(NetworkHandler handler)	{
		server = new Server();
		this.handler = handler;
	}
	
	public void initAndBind(int port) throws IOException {
	    Server server = new Server();
	    server.start();
	    server.bind(port+1, port);
	    
	    Kryo kryo = server.getKryo();
	    kryo.register(GameConnectionRequest.class);
	    kryo.register(GameConnectionResponse.class);
	    kryo.register(GameRequest.class);
	    kryo.register(GameResponse.class);
	    kryo.register(java.util.HashMap.class);
	   // kryo.register(com.dc0d.iiridarts.venture.networking.EntityUpdatePacket.class);
	    //kryo.register(.class);
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof GameConnectionRequest) {
	        	   GameConnectionRequest request = (GameConnectionRequest)object;
	        	   GameConnectionResponse response = new GameConnectionResponse();
	              if (request.password.equalsIgnoreCase("bofolo37*")){
		              response.entityUpdates = handler.venture.entityUpdatePackets;
		              response.response = "ready for handshake";
		              connection.sendUDP(response);
	              } else {
	            	  response.response = "invalid password";
	            	  connection.sendUDP(response);
	            	  connection.close();
	              }
	              
	           } else if (object instanceof GameRequest) {
	        	   GameRequest request = (GameRequest)object;
	        	   if (request.request.equals("update")) {
	        		  GameResponse response = new GameResponse();
		              response.entityUpdates = handler.venture.entityUpdatePackets;
		              handler.venture.players.put(request.player.id, request.player);
		              connection.sendUDP(response);
	        	   }
	           }
              if (object instanceof GameRequest) {
            	  GameRequest request = (GameRequest)object;
	              if (request.request.equals("update")) {
		              GameResponse response = new GameResponse();
		              response.entityUpdates = handler.venture.entityUpdatePackets;
		              connection.sendUDP(response);
	              }
	              /*
	               * TODO Fix this networking stuff up in the server.
	               * I need it to add player when they connect and remove them when they disconnect.
	               */
	           }
	        }
	     });
	}
	
	
	
	public void closeServer() {
		server.close();
	}
}

class GameConnectionRequest {
   public int type;
   public String password;
   public Player player;
}
class GameConnectionResponse {
   public String response;
   public HashMap<String, EntityUpdatePacket> entityUpdates;
}

class GameRequest {
   public String request;
   public Player player;
}
class GameResponse {
   public String response;
   public HashMap<String, EntityUpdatePacket> entityUpdates;
}
