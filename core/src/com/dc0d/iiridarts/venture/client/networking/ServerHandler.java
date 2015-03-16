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
	ServerHandler client;
	Server server;
	NetworkResponse request;
	ArrayList<Packet> pendingRequests;
	HashMap<Integer, HashMap<Integer, Tile>> tileUpdates;

	public ServerHandler(VentureServer venture, ServerWorld world) {
		this.ventureServer = venture;
		server = new Server();
		request = new NetworkResponse();
	}

	public void initServer(int port) throws IOException {
		initAndBind(port);
	}

	public void initAndBind(int port) throws IOException {
		Server server = new Server();
		server.start();
		server.bind(port + 1, port);

		Kryo kryo = server.getKryo();
		kryo.register(NetworkConnectionRequest.class);
		kryo.register(NetworkConnectionResponse.class);
		kryo.register(NetworkRequest.class);
		kryo.register(NetworkResponse.class);
		kryo.register(java.util.HashMap.class);
		// kryo.register(com.dc0d.iiridarts.venture.networking.EntityUpdatePacket.class);
		// kryo.register(.class);
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof NetworkConnectionRequest) {
					NetworkConnectionRequest request = (NetworkConnectionRequest) object;
					NetworkConnectionResponse response = new NetworkConnectionResponse();
					if (request.password.equalsIgnoreCase("bofolo37*")) {
						//response.packets = ventureServer.packets;
						response.response = "ready for handshake";
						connection.sendUDP(response);
					} else {
						response.response = "invalid password";
						connection.sendUDP(response);
						connection.close();
					}
				}
			}
		});
	}
}
