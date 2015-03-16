/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

/**
 * Low-level proxy for receiving requests and sending responses 
 * @author Thomas
 *
 */

public class KryoNetServer {

	Server server;
	ServerNetworkHandler handler;

	/**
	 * Constructor for KryoNetServer
	 * @param handler input ServerNetworkHandler for proxy-level communication with the game handler
	 */
	
	public KryoNetServer(ServerNetworkHandler handler) {
		server = new Server();
		this.handler = handler;
	}
	
	/**
	 * Starts the server proxy
	 * @param port input value of port to bind
	 * @throws IOException
	 */
	
	public void initAndBind(int port) throws IOException {
		Server server = new Server();
		server.start();
		server.bind(port + 1, port);

		//Kryo kryo = server.getKryo();
		// kryo.register(com.dc0d.iiridarts.venture.networking.EntityUpdatePacket.class);
		// kryo.register(.class);
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof NetworkConnectionRequest) {
					NetworkConnectionRequest request = (NetworkConnectionRequest) object;
					NetworkConnectionResponse response = new NetworkConnectionResponse();
					if (request.password.equalsIgnoreCase("bofolo37*")) {
						//response.packets = handler.venture.packets;
						response.response = "ready for handshake";
						connection.sendUDP(response);
					} else {
						response.response = "invalid password";
						connection.sendUDP(response);
						connection.close();
					}

				} else if (object instanceof NetworkRequest) {
					NetworkRequest request = (NetworkRequest) object;
					if (request.request.equals("update")) {
						NetworkResponse response = new NetworkResponse();
						//response.packets = handler.venture.packets;
						connection.sendUDP(response);
					}
				}
				if (object instanceof NetworkRequest) {
					NetworkRequest request = (NetworkRequest) object;
					if (request.request.equals("update")) {
						NetworkResponse response = new NetworkResponse();
						//response.packets = handler.venture.packets;
						connection.sendUDP(response);
					}
					/*
					 * TODO Fix this networking stuff up in the server. I need
					 * it to add player when they connect and remove them when
					 * they disconnect.
					 */
				}
			}
		});
	}
	
	/**
	 * Handles closing the low-level proxy
	 */
	
	public void closeServer() {
		server.close();
	}
}
