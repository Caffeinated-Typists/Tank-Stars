package com.ctypists.tankstars;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import sun.tools.jar.Main;

public class TankStars extends Game {
	/**
	 * Called when the {@link Application} is first created.
	 */
	public static final int WIDTH = 1344;
	public static final int HEIGHT = 621;


	@Override
	public void create() {
		setScreen(new MainScreen(this));
	}
}
