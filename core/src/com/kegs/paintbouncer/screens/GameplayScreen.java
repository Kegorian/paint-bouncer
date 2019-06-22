package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kegs.paintbouncer.entities.Player;
import com.kegs.paintbouncer.platform.PlatformSpawner;

public class GameplayScreen extends GameScreen {

    // Fields
    private World gameWorld;
    private Box2DDebugRenderer debugRenderer;
    private Player player;
    private PlatformSpawner platformSpawner;


    public GameplayScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        super(spriteBatch, parent);

        // Set up physics
        gameWorld = new World(new Vector2(0, -100), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(gameWorld);
        platformSpawner = new PlatformSpawner(gameWorld);
    }

    @Override
    public void render(float delta) {
        update(delta);

        super.render(delta);

        // Draw Sprites
        spriteBatch.begin();
        platformSpawner.render(spriteBatch);
        spriteBatch.draw(player, player.getX(), player.getY());
        spriteBatch.end();

        // Debug Render
        debugRenderer.render(gameWorld, camera.combined);
    }

    @Override
    public void dispose() {
        super.dispose();

        platformSpawner.dispose();
        gameWorld.dispose();
        debugRenderer.dispose();
    }

    protected void update(float delta) {
        gameWorld.step(1/60f, 6, 2);

        platformSpawner.update(delta);
        player.update(delta);

        // Move camera
        // camera.position.y = camera.position.y - 1.2f;
    }
}
