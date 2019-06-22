package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends GameScreen {

    // Fields
    private Texture background;
    private BitmapFont menuFont;

    public MainMenuScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        super(spriteBatch, parent);

        background = new Texture(Gdx.files.internal("graphics/backgrounds/main_menu.png"));

        menuFont = new BitmapFont();
        // TODO: Add a font file and remove below
        menuFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        menuFont.getData().setScale(2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);
        menuFont.draw(spriteBatch, "Paint Bouncer!", 150, 700);
        menuFont.draw(spriteBatch, "Click to Start!", 150, 200);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        menuFont.dispose();
    }

    protected void update(float delta) {
        // Move onto the next screen if touched.
        if (Gdx.app.getInput().isTouched()) {
            parent.setScreen(ScreenType.GAMEPLAY);
            dispose();
        }
    }
}
