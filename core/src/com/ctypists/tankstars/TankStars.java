package com.ctypists.tankstars;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;

public class TankStars extends Game {
	/**
	 * Called when the {@link Application} is first created.
	 */
	public static final int WIDTH = 1344;
	public static final int HEIGHT = 621;
	public boolean save_state = false;


	@Override
	public void create() {
		setScreen(new MainScreen(this));
	}
}
