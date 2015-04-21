/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.io.IOException;
import java.util.ArrayList;

import com.dc0d.iiridarts.venture.client.Constants;
import com.dc0d.iiridarts.venture.client.Venture;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientNetworkHandler {
	
	private Client client;
	ArrayList<Packet> pendingRequests;
	Venture venture;
	ClientUpdateHandler updateHandler;
	
	public ClientNetworkHandler()	{
		client = new Client();
		updateHandler = new ClientUpdateHandler();
	}
	
	/**
	 * Attempts to connect client to server
	 * @param ipAddress
	 * @param port
	 * @param password input string for password protected servers
	 * @throws IOException
	 */
	
	public void initAndConnect(String ipAddress, int port, String password) throws IOException {
		initAndConnect(ipAddress, Constants.NETWORKTIMEOUT, port);
		attemptHandshake(password);
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
	    kryo.register(java.util.HashMap.class);
		attemptHandshake("bofolo37*");
	    client.addListener(new Listener() {
	        public void Network (Connection connection, Object object) {
	           if (object instanceof NetworkConnectionResponse) {
	        	  NetworkConnectionResponse response = (NetworkConnectionResponse)object;
	        	  if(response.response.equalsIgnoreCase("ready for handshake")){
	        		  NetworkRequest request = new NetworkRequest();
		        	  connection.sendUDP(request);
		        	//  for(int p = 0; p <= response.entityUpdates.size(); p++){
		        	//	  response.entityUpdates.get(p).isRemote = true;
		        	//	  //handler.venture.players.put(response.entityUpdates.get(p).id, response.entityUpdates.get(p));
		        	//  }
		        	  
		        	  client.sendUDP(request);
	        	  }
	              //System.out.println(request.request);
	           }
	           if (object instanceof NetworkResponse) {
	        	   
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
	 * @param password input string for connecting to protected server
	 */
	
	public void attemptHandshake(String password) {
		//if (client.isConnected()){
			NetworkConnectionRequest request = new NetworkConnectionRequest();
			request.password = password;
			client.sendUDP(request);
		//}
	}
}

