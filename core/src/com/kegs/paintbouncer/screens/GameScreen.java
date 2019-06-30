package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * An extension of ScreenAdapter and therefore Screen with some fields and methods
 * that are needed between screens within the game. For example a ScreenManager
 * and a camera.
 */
public abstract class GameScreen extends ScreenAdapter {

    // Fields
    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected ScreenManager parent;

    /**
     * Creates a new instance of GameScreen
     * @param spriteBatch The current SpriteBatch
     * @param parent The current ScreenManager
     */
    public GameScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        this.spriteBatch = spriteBatch;
        this.parent = parent;

        camera = new OrthographicCamera();
        viewport = new FitViewport(480, 800, camera);
        camera.setToOrtho(false, viewport.getWorldWidth(),
                viewport.getWorldHeight());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Renders the screen to the window.
     * @param delta Time between calls.
     */
    @Override
    public void render(float delta) {
        // Update Screen
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    /**
     * Disposes all objects.
     */
    @Override
    public void dispose() {

    }

    /**
     * Updates the screen. This is used to update position and also to handle
     * input.
     * @param delta The time between calls.
     */
    protected abstract void update(float delta);
}
