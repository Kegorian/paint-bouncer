package com.kegs.paintbouncer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kegs.paintbouncer.PaintBouncer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Paint Bouncer";
		config.resizable = false;
		config.width = 480;
		config.height = 800;

		new LwjglApplication(new PaintBouncer(), config);
	}
}
