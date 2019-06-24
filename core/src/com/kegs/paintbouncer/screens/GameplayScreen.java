package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kegs.paintbouncer.entities.Player;
import com.kegs.paintbouncer.platform.PlatformSpawner;

/**
 * The GameplayScreen where that will handle the main part of the game. From
 * spawning platforms to handling user input that changes the colour.
 */
public class GameplayScreen extends GameScreen {

    // Fields
    private Texture background;
    private World gameWorld;
    private Body leftWall, rightWall;
    private Box2DDebugRenderer debugRenderer;
    private Player player;
    private PlatformSpawner platformSpawner;

    /**
     * Creates new instance of GameplayScreen
     * @param spriteBatch The current SpriteBatch
     * @param parent The current ScreenManager
     */
    public GameplayScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        super(spriteBatch, parent);

        camera.zoom = 0.5f;

        // Set up physics
        gameWorld = new World(new Vector2(0, -250), true);
        debugRenderer = new Box2DDebugRenderer();

        background = new Texture(Gdx.files.internal("graphics/backgrounds/game_play.png"));
        player = new Player(gameWorld);
        platformSpawner = new PlatformSpawner(gameWorld);

        // Set up collision check.
        gameWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                player.checkContact(contact);
            }

            @Override
            public void endContact(Contact contact) { /* Left Blank. */ }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) { /* Left Blank. */ }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) { /* Left Blank. */ }
        });

        // All Walls
        addWalls();
    }

    /**
     * Renders the screen to the window.
     * @param delta Time between calls.
     */
    @Override
    public void render(float delta) {
        update(delta);

        super.render(delta);

        // Draw Sprites
        spriteBatch.begin();
        spriteBatch.draw(background, 0, camera.position.y - Gdx.graphics.getHeight() / 2.0f);

        platformSpawner.render(spriteBatch);

        player.setColor(player.getColor());
        player.draw(spriteBatch);

        spriteBatch.setColor(Color.WHITE);
        spriteBatch.end();

        // Debug Render
        debugRenderer.render(gameWorld, camera.combined);
    }

    /**
     * Disposes all objects.
     */
    @Override
    public void dispose() {
        super.dispose();

        platformSpawner.dispose();
        gameWorld.dispose();
        debugRenderer.dispose();
    }

    /**
     * Updates the screen. This is used to update position and also to handle
     * input.
     * @param delta The time between calls.
     */
    protected void update(float delta) {
        gameWorld.step(1/60f, 6, 2);

        platformSpawner.update(delta);
        player.update(delta);

        // Move camera
        // camera.position.y = camera.position.y - 0.4f; TODO: Change this to follow the ball, somewhat.
        // Update Wall position to match the camera position.
        leftWall.setTransform(new Vector2(camera.position.x - camera.viewportWidth / 4 - 9.5f, camera.position.y), 0);
        rightWall.setTransform(new Vector2(camera.position.x + camera.viewportWidth / 4 + 10f, camera.position.y), 0);
    }

    /**
     * Adds the walls to the left and right and sets up their physics. This is
     * moved here to avoid clutter in the constructor, but could be there as well.
     */
    private void addWalls() {
        float halfWidth = Gdx.graphics.getWidth() / 2.0f;
        float halfHeight = Gdx.graphics.getHeight() / 2.0f;

        // Left Wall
        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.type = BodyDef.BodyType.KinematicBody;
        //platformBodyDef.position.set(new Vector2(halfWidth / 2 - 8, halfHeight + 5));
        platformBodyDef.position.set(new Vector2(camera.position.x, camera.position.y));
        leftWall = gameWorld.createBody(platformBodyDef);
        PolygonShape platformShape = new PolygonShape();
        platformShape.setAsBox(10, halfHeight + 5);
        leftWall.createFixture(platformShape, 0.0f);

        // Right Wall
        platformBodyDef.position.set(new Vector2(halfWidth + (halfWidth / 2) + 8, halfHeight + 5));
        rightWall = gameWorld.createBody(platformBodyDef);
        rightWall.createFixture(platformShape, 0.0f);
        platformShape.dispose();
    }
}
