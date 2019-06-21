package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class ScreenManager {

    // Fields
    private Game game;
    private HashMap<ScreenType, Screen> screens;

    public ScreenManager(Game game, SpriteBatch spriteBatch) {
        this.game = game;

        screens = new HashMap<ScreenType, Screen>();
        screens.put(ScreenType.MAINMENU, new MainMenuScreen(spriteBatch, this));
        screens.put(ScreenType.GAMEPLAY, new GameplayScreen(spriteBatch, this));

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
}
