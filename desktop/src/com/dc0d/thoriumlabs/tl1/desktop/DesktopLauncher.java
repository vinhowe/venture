package com.dc0d.thoriumlabs.tl1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dc0d.thoriumlabs.venture.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project Venture";
		config.vSyncEnabled = true;
		new LwjglApplication(new Game(), config);
	}
}
