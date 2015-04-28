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

public class KryoNetClient {
	
	private Client client;
	Venture venture;
	ClientUpdateHandler updateHandler;
	
	public KryoNetClient()	{
		client = new Client();
		updateHandler = new ClientUpdateHandler();
	}
	
	/**
	 * Initializes client and connects to server
	 * @param ipAddress
	 * @param timeout
	 * @param tcpPort
	 * @param udpPort
	 * @throws IOException
	 */
	public void initAndConnect(String ipAddress, int port, String password) throws IOException{
	    Kryo kryo = client.getKryo();
		kryo.register(NetworkConnectionRequest.class);
		kryo.register(NetworkConnectionResponse.class);
		kryo.register(NetworkRequest.class);
		kryo.register(NetworkResponse.class);
		kryo.register(java.util.HashMap.class);
		client.start();
		client.connect(Constants.NETWORKTIMEOUT, ipAddress, port+1, port);
		attemptHandshake(password);
	    client.addListener(new Listener() {
	    	public void received (Connection connection, Object object) {
	           if (object instanceof NetworkConnectionResponse) {
	        	  NetworkConnectionResponse response = (NetworkConnectionResponse)object;
	        	  if(response.response.equalsIgnoreCase("ready for handshake")){
	        		  NetworkRequest request = new NetworkRequest();
		        	  
		        	  connection.sendTCP(request);
	        	  }
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
			client.sendTCP(request);
		//}
	}
}

