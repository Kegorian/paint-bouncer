package com.kegs.paintbouncer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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

		float delta = Gdx.graphics.getDeltaTime();

		// Render the game world.

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		screenManager.dispose();
	}

	public SpriteBatch getBatch() { return batch; }
}

/**
 * Some Ideas:
 *
 * Random platforms spawn from either side and you need to keep moving down before
 * your character reaches the top of the screen.
 *
 * But you also need to change colour before you hit the next platform.
 * You need to make sure that you are of the same colour at the platform you
 * are landing on.
 */
