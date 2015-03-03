package com.dc0d.iiridarts.venture.networking;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientNetworkHandler {
	
	Client client;
	NetworkHandler handler;
	
	public ClientNetworkHandler(NetworkHandler handler)	{
		client = new Client();
		this.handler = handler;
	}
	/**
	 * Initializes client and connects to server
	 * @param ipAddress
	 * @param timeout
	 * @param tcpPort
	 * @param udpPort
	 * @throws IOException
	 */
	public void initAndConnect(String ipAddress, int timeout, int port) throws IOException{
		client.start();
		client.connect(timeout, ipAddress, port+1, port);
	    Kryo kryo = client.getKryo();
	    kryo.register(GameConnectionRequest.class);
	    kryo.register(GameConnectionResponse.class);
	    kryo.register(GameRequest.class);
	    kryo.register(GameResponse.class);
	    kryo.register(java.util.HashMap.class);
		attemptHandshake("bofolo37*");
	    client.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof GameConnectionResponse) {
	        	  GameConnectionResponse response = (GameConnectionResponse)object;
	        	  if(response.response.equalsIgnoreCase("ready for handshake")){
		        	  GameRequest request = new GameRequest();
		        	  request.player = handler.venture.player;
		        	  request.request = "update";
		        	  connection.sendUDP(request);
		        	  for(int p = 0; p <= response.entityUpdates.size(); p++){
		        		  response.entityUpdates.get(p).isRemote = true;
		        		  //handler.venture.players.put(response.entityUpdates.get(p).id, response.entityUpdates.get(p));
		        	  }
		        	  
		        	  client.sendUDP(request);
	        	  }
	              //System.out.println(request.request);
	           }
	           if (object instanceof GameResponse) {
	        	   
	           }
	        }
	     });
	}
	
	/**
	 * Closes client connection to server
	 */
	
	public void close(){
		client.stop();
	}
	
	public void sendUDPRequest(Object request){
		client.sendUDP(request);
	}
	
	/**
	 * Attempts to perfom a handshake with Server if connected.
	 */
	public void attemptHandshake(String password) {
		//if (client.isConnected()){
			GameConnectionRequest request = new GameConnectionRequest();
			request.password = password;
			request.type = 0;
			client.sendUDP(request);
		//}
	}
}
