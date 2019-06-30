package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

/**
 * Handles the changing, creation and deletion of GameScreens.
 */
public class ScreenManager {

    // Fields
    private Game game;
    private HashMap<ScreenType, Screen> screens;
    private SpriteBatch spriteBatch;

    /**
     * Creates a new instance of ScreenManager.
     * @param game The current Game object.
     * @param spriteBatch The current SpriteBatch.
     */
    public ScreenManager(Game game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;

        screens = new HashMap<ScreenType, Screen>();
        screens.put(ScreenType.MAINMENU, new MainMenuScreen(spriteBatch, this));
        screens.put(ScreenType.GAMEPLAY, new GameplayScreen(spriteBatch, this));
        screens.put(ScreenType.GAMEOVER, new GameOverScreen(spriteBatch, this, 0));

        // Default to Main Menu
        setScreen(ScreenType.MAINMENU);
    }

    /**
     * Changes the screen of the game.
     * @param type The screen type value.
     */
    public void setScreen(ScreenType type) {
        game.setScreen(screens.get(type));
    }

    /**
     * Runs through all the screens and disposes of everything.
     */
    public void dispose() {
        for (Screen screen : screens.values()) {
            screen.dispose();
        }
    }

    /**
     * Creates a new instance of GamePlayScreen and changes to it.
     */
    public void newGame() {
        screens.put(ScreenType.GAMEPLAY, new GameplayScreen(spriteBatch, this));
        setScreen(ScreenType.GAMEPLAY);
    }

    /**
     *
     * @param score
     */
    public void gameOver(int score) {
        screens.put(ScreenType.GAMEOVER, new GameOverScreen(spriteBatch, this, score));
        setScreen(ScreenType.GAMEOVER);
    }
}
