package com.dc0d.oxidearts.venture.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dc0d.iiridarts.venture.Game;

public class AndroidLauncher /*extends AndroidApplication*/ {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Venture(), config);
	}
}
