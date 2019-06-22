package com.kegs.paintbouncer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kegs.paintbouncer.screens.ScreenManager;

public class PaintBouncer extends Game {

	// Fields
	private SpriteBatch batch;
	private ScreenManager screenManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenManager = new ScreenManager(this, batch);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		screenManager.dispose();
	}
}

