package com.dc0d.iiridarts.venture.networking;

import java.io.IOException;

import com.dc0d.iiridarts.venture.Constants;
import com.dc0d.iiridarts.venture.ServerWorld;
import com.dc0d.iiridarts.venture.Venture;
import com.dc0d.iiridarts.venture.VentureServer;
import com.dc0d.iiridarts.venture.World;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerHandler {
	VentureServer ventureServer;
	ServerWorld world;
	ServerHandler client;
	Server server;
	GameRequest request;
	
	public ServerHandler(VentureServer venture, ServerWorld world) {
		this.ventureServer = venture;
		server = new Server();
		request = new GameRequest();
	}
	
	public void initServer(int port) throws IOException {
		initAndBind(port);
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
	    kryo.register(com.dc0d.iiridarts.venture.networking.EntityUpdatePacket.class);
	    //kryo.register(.class);
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof GameConnectionRequest) {
	        	   GameConnectionRequest request = (GameConnectionRequest)object;
	        	   GameConnectionResponse response = new GameConnectionResponse();
	              if (request.password.equalsIgnoreCase("bofolo37*")){
		              response.entityUpdates = ventureServer.entityUpdatePackets;
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
		              response.entityUpdates = ventureServer.entityUpdatePackets;
		              ventureServer.players.put(request.player.id, request.player);
		              connection.sendUDP(response);
	        	   }
	           }
              if (object instanceof GameRequest) {
            	  GameRequest request = (GameRequest)object;
	              if (request.request.equals("update")) {
		              GameResponse response = new GameResponse();
		              response.entityUpdates = ventureServer.entityUpdatePackets;
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
	
}
