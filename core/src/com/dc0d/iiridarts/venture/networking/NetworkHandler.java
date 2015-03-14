package com.dc0d.iiridarts.venture.networking;

import java.io.IOException;

import com.dc0d.iiridarts.venture.Constants;
import com.dc0d.iiridarts.venture.Venture;
import com.dc0d.iiridarts.venture.World;

public class NetworkHandler {
	Venture venture;
	World world;
	ClientNetworkHandler clientHandler;
	KryoNetServer server;
	GameRequest request;
	
	public NetworkHandler(Venture venture, World world) {
		this.venture = venture;
		clientHandler = new ClientNetworkHandler();
		server = new KryoNetServer(this);
		request = new GameRequest();
	}
	
	public void initClient(int port) throws IOException {
		clientHandler.initAndConnect("127.0.0.1", Constants.NETWORKTIMEOUT, port);
		clientHandler.attemptHandshake("bofolo37*");
	}
	
	public void initServer(int port) throws IOException {
		server.initAndBind(port);
	}
	
	public void clientUpdate() {
		request.player = venture.player;
	}
}
