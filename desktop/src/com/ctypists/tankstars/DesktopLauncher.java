package com.ctypists.tankstars;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ctypists.tankstars.TankStars;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//		config.setWindowSizeLimits(1120, 630, 1120, 630);
		config.setForegroundFPS(60);
		config.setTitle("TankStars");
		config.setBackBufferConfig(8,8,8,8,16,0, 10);
		config.setResizable(false);

		new Lwjgl3Application(new TankStars(), config);
	}
}
