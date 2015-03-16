/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client.networking;

import java.io.IOException;

import com.dc0d.iiridarts.venture.client.Constants;
import com.dc0d.iiridarts.venture.client.Venture;
import com.dc0d.iiridarts.venture.client.World;

public class ServerNetworkHandler {
	Venture venture;
	World world;
	ClientNetworkHandler clientHandler;
	KryoNetServer server;
	NetworkRequest request;
	
	public ServerNetworkHandler(Venture venture, World world) {
		this.venture = venture;
		clientHandler = new ClientNetworkHandler();
		server = new KryoNetServer(this);
		request = new NetworkRequest();
	}
	
	public void initClient(int port) throws IOException {
		clientHandler.initAndConnect("127.0.0.1", Constants.NETWORKTIMEOUT, port);
		clientHandler.attemptHandshake("bofolo37*");
	}
	
	public void initServer(int port) throws IOException {
		server.initAndBind(port);
	}
}
