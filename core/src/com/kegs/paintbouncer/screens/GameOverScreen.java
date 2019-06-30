package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kegs.paintbouncer.userinterface.UserInterface;

public class GameOverScreen extends GameScreen {

    // Fields
    private Texture background;
    private UserInterface ui;

    /**
     * Creates a new instance of GameScreen
     *
     * @param spriteBatch The current SpriteBatch
     * @param parent      The current ScreenManager
     */
    public GameOverScreen(SpriteBatch spriteBatch, final ScreenManager parent, int score) {
        super(spriteBatch, parent);

        background = new Texture(Gdx.files.internal("graphics/backgrounds/game_play.png"));
        ui = new UserInterface();

        // Game Over text.
        Label lbGameOver = new Label("Game Over!", ui.getSkin(), "title");
        lbGameOver.setPosition(camera.viewportWidth / 2, camera.viewportHeight - 75, Align.center);

        // Score Text.
        Label lbScore = new Label("You scored: " + score, ui.getSkin(), "default");
        lbScore.setPosition(camera.viewportWidth / 2, 400, Align.center);

        // Restart Button
        TextButton restartButton = new TextButton("Restart!", ui.getSkin(), "default");
        restartButton.setPosition(camera.viewportWidth / 2, 200, Align.center);
        restartButton.setSize(150, 40);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.newGame();
            }
        });

        ui.addActor(lbGameOver);
        ui.addActor(lbScore);
        ui.addActor(restartButton);

        Gdx.input.setInputProcessor(ui.getStage());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        ui.resize(width, height);
    }

    /**
     * Renders the screen to the window.
     * @param delta Time between calls.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, camera.position.y - viewport.getWorldHeight() / 2.0f);
        spriteBatch.end();

        ui.render(delta);
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
    @Override
    protected void update(float delta) {

    }
}
