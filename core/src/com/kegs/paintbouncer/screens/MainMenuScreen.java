package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen extends GameScreen {

    // Fields
    private Texture background;

    /**
     * Creates a new instance of MainMenuScreen
     * @param spriteBatch The current SpriteBatch9
     * @param parent The current ScreenManager
     */
    public MainMenuScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        super(spriteBatch, parent);

        background = new Texture(Gdx.files.internal("graphics/backgrounds/main_menu.png"));

        Label lbTitle = new Label("Paint Bouncer", uiSkin, "title");
        lbTitle.setPosition(camera.viewportWidth / 2.0f, camera.viewportHeight - 120, Align.center);
        Label lbStart = new Label("Click to Start!", uiSkin, "title");
        lbStart.setPosition(camera.viewportWidth / 2.0f, 250, Align.center);
        stage.addActor(lbTitle);
        stage.addActor(lbStart);
    }

    /**
     * Renders the screen to the window.
     * @param delta Time between calls.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);
        spriteBatch.end();

        stage.draw();
    }

    /**
     * Disposes all objects.
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Updates the screen. This is used to update position and also to handle
     * input.
     * @param delta The time between calls.
     */
    protected void update(float delta) {
        stage.act(delta);

        // Move onto the next screen if touched.
        if (Gdx.app.getInput().isTouched()) {
            parent.setScreen(ScreenType.GAMEPLAY);
            dispose();
        }
    }
}
