/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January-March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dc0d.iiridarts.venture.client.ServerWorld;
import com.dc0d.iiridarts.venture.client.VentureServer;
import com.dc0d.iiridarts.venture.client.tiles.Tile;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerHandler {
	VentureServer ventureServer;
	ServerWorld world;
	Server server;
	NetworkResponse request;
	ArrayList<Packet> pendingRequests;
	HashMap<Integer, HashMap<Integer, Tile>> tileUpdates;

	public ServerHandler(VentureServer venture, ServerWorld world) {
		this.ventureServer = venture;
		server = new Server();
		request = new NetworkResponse();
	}

	public void initAndBind(int port) throws IOException {
		Server server = new Server();
		Kryo kryo = server.getKryo();
		//Packets MUST be registered in the same order on client and server
		kryo.register(NetworkConnectionRequest.class);
		kryo.register(NetworkConnectionResponse.class);
		kryo.register(NetworkRequest.class);
		kryo.register(NetworkResponse.class);
		kryo.register(java.util.HashMap.class);
		// kryo.register(.class);
		server.start();
		server.bind(port + 1, port);
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				//world.venture.logger.finer("Received:" + object.getClass());
				if (object instanceof NetworkConnectionRequest) {
					NetworkConnectionRequest request = (NetworkConnectionRequest) object;
					NetworkConnectionResponse response = new NetworkConnectionResponse();
					//world.venture.logger.finer(request.password);
					if (request.password.equalsIgnoreCase("bofolo37*")) {
						//response.packets = ventureServer.packets;
						response.response = "ready for handshake";
						connection.sendTCP(response);
						world.venture.logger.finer("Accepted connection: " + connection.getID());
					} else {
						response.response = "invalid password";
						connection.sendTCP(response);
						connection.close();
					}
				}
			}
		});

	}
}
