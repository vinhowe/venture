package com.dc0d.iiridarts.venture.networking;

import java.io.IOException;

import com.dc0d.iiridarts.venture.Constants;
import com.dc0d.iiridarts.venture.Venture;
import com.dc0d.iiridarts.venture.World;

public class NetworkHandler {
	Venture venture;
	World world;
	KryoNetClient client;
	KryoNetServer server;
	GameRequest request;
	
	public NetworkHandler(Venture venture, World world) {
		this.venture = venture;
		client = new KryoNetClient(this);
		server = new KryoNetServer(this);
		request = new GameRequest();
	}
	
	public void initClient(int port) throws IOException {
		client.initAndConnect("127.0.0.1", Constants.NETWORKTIMEOUT, port);
		client.attemptHandshake("bofolo37*");
	}
	
	public void initServer(int port) throws IOException {
		server.initAndBind(port);
	}
	
	public void clientUpdate() {
		request.player = venture.player;
	}
}
