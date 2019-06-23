package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kegs.paintbouncer.entities.Player;
import com.kegs.paintbouncer.platform.PlatformSpawner;

public class GameplayScreen extends GameScreen {

    // Fields
    private Texture background;
    private World gameWorld;
    private Box2DDebugRenderer debugRenderer;
    private Player player;
    private PlatformSpawner platformSpawner;


    public GameplayScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        super(spriteBatch, parent);

        // Set up physics
        gameWorld = new World(new Vector2(0, -150), true);
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
            public void endContact(Contact contact) { }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) { }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) { }
        });

        // All Walls
        addWalls();
    }

    @Override
    public void render(float delta) {
        update(delta);

        super.render(delta);

        // Draw Sprites
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);

        platformSpawner.render(spriteBatch);

        player.setColor(player.getColor());
        player.draw(spriteBatch);

        spriteBatch.setColor(Color.WHITE);
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

    private void addWalls() {
        // Left Wall
        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.type = BodyDef.BodyType.KinematicBody;
        platformBodyDef.position.set(new Vector2(-8, (Gdx.graphics.getHeight() / 2.0f) + 5));
        Body body = gameWorld.createBody(platformBodyDef);
        PolygonShape platformShape = new PolygonShape();
        platformShape.setAsBox(10, (Gdx.graphics.getHeight() / 2.0f) + 5);
        body.createFixture(platformShape, 0.0f);

        // Right Wall
        platformBodyDef.position.set(new Vector2(Gdx.graphics.getWidth() + 8, (Gdx.graphics.getHeight() / 2.0f) + 5));
        Body bodyTwo = gameWorld.createBody(platformBodyDef);
        bodyTwo.createFixture(platformShape, 0.0f);
        platformShape.dispose();
    }
}
