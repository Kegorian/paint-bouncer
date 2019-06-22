package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameScreen extends ScreenAdapter {

    // Fields
    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected ScreenManager parent;

    public GameScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        this.spriteBatch = spriteBatch;
        this.parent = parent;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.app.getGraphics().getWidth(),
                Gdx.app.getGraphics().getHeight());
    }

    @Override
    public void render(float delta) {
        // Update Screen
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {

    }

    protected abstract void update(float delta);
}
