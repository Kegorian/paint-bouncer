package com.kegs.paintbouncer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kegs.paintbouncer.colors.GameColors;
import com.kegs.paintbouncer.entities.Player;
import com.kegs.paintbouncer.input.GameGestureListener;
import com.kegs.paintbouncer.platform.Platform;
import com.kegs.paintbouncer.platform.PlatformSpawner;
import com.kegs.paintbouncer.userinterface.UserInterface;

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
    private ShapeRenderer shapeRenderer;
    private UserInterface ui;
    private Label lbScore, lbCountdown;
    private int score, countdownStart;
    private float startTimer;

    /**
     * Creates new instance of GameplayScreen
     * @param spriteBatch The current SpriteBatch
     * @param parent The current ScreenManager
     */
    public GameplayScreen(SpriteBatch spriteBatch, ScreenManager parent) {
        super(spriteBatch, parent);

        camera.zoom = 0.5f;
        camera.position.y = camera.viewportHeight;

        // Set up physics
        gameWorld = new World(new Vector2(0, -250), true);
        debugRenderer = new Box2DDebugRenderer();

        background = new Texture(Gdx.files.internal("graphics/backgrounds/game_play.png"));
        player = new Player(gameWorld);
        platformSpawner = new PlatformSpawner(gameWorld);
        shapeRenderer = new ShapeRenderer();
        score = 0;
        startTimer = 0.0f;
        countdownStart = 3;

        // Set up collision check.
        gameWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                checkContact(contact);
            }

            @Override
            public void endContact(Contact contact) { /* Left Blank. */ }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) { /* Left Blank. */ }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) { /* Left Blank. */ }
        });

        addRailGuards();

        // Set up input.
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(new GameGestureListener(player));
        im.addProcessor(gd);

        Gdx.input.setInputProcessor(im);

        // Set up font.
        ui = new UserInterface();
        lbScore = new Label("", ui.getSkin(), "default");
        lbScore.setAlignment(Align.center);
        lbScore.setPosition(camera.viewportWidth / 2, camera.viewportHeight - 45);

        lbCountdown = new Label("3", ui.getSkin(), "title");
        lbCountdown.setAlignment(Align.center);
        lbCountdown.setPosition(camera.viewportWidth / 2, camera.viewportHeight / 2);

        ui.addActor(lbScore);
        ui.addActor(lbCountdown);
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
        update(delta);

        super.render(delta);

        // Draw Sprites
        spriteBatch.begin();
        spriteBatch.draw(background, 0, camera.position.y - viewport.getWorldHeight() / 2.0f);
        platformSpawner.render(spriteBatch);
        player.setColor(player.getColor());
        player.draw(spriteBatch);
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.end();

        addWalls();

        ui.render(delta);

        // Debug Render
        // debugRenderer.render(gameWorld, camera.combined);
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
        // Move camera
        float lerp = 0.75f;
        camera.position.y += (player.getY() - camera.position.y - (camera.viewportHeight / 6.0f)) * lerp * delta;

        if (startTimer < countdownStart + 1) {
            startTimer += delta;

            lbCountdown.setText(countdownStart - (int)startTimer);

            player.update(delta);

            if (startTimer > countdownStart)
                lbCountdown.setText("GO!");

            lbCountdown.setPosition(camera.viewportWidth / 2, camera.viewportHeight / 2);
        }
        else {
            lbCountdown.setText("");

            gameWorld.step(1/60f, 6, 2);
            player.update(delta);

            // Update Wall position to match the camera position.
            leftWall.setTransform(new Vector2(camera.position.x - camera.viewportWidth / 4, camera.position.y), 0);
            rightWall.setTransform(new Vector2(camera.position.x + camera.viewportWidth / 4, camera.position.y), 0);

            // Spawn new platforms.
            if (camera.position.y - (camera.viewportHeight * 0.25) < platformSpawner.getLastPlatform().getY() + 400.0f) {
                platformSpawner.spawnPlatform();
                platformSpawner.removePlatform();
            }

            platformSpawner.update(delta);

            // Update Text
            lbScore.setText("Score: " + score);
            lbScore.setPosition(camera.viewportWidth / 2, camera.viewportHeight - 45);
        }
    }

    /**
     * Add zones at the edges of the screen so the player can see what direction
     * they need to swipe to select the colour.
     */
    private void addWalls() {
        int size = 10;

        float leftSide = camera.position.x - camera.viewportWidth / 4;
        float rightSide = camera.position.x + camera.viewportWidth / 4 - size;
        float topSide = camera.position.y + camera.viewportHeight / 4 - size;
        float botSide = camera.position.y - camera.viewportHeight / 4;

        int width = viewport.getScreenWidth();
        int height = -viewport.getScreenHeight();

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GameColors.BLUE);
        shapeRenderer.rect(rightSide, topSide, size, height);
        shapeRenderer.setColor(GameColors.RED);
        shapeRenderer.rect(leftSide, topSide, size, height);
        shapeRenderer.setColor(GameColors.ORANGE);
        shapeRenderer.rect(leftSide, topSide, width, size);
        shapeRenderer.setColor(GameColors.GREEN);
        shapeRenderer.rect(leftSide, botSide, width, size);
        shapeRenderer.end();
    }

    /**
     * Adds the walls to the left and right and sets up their physics. This is
     * moved here to avoid clutter in the constructor, but could be there as well.
     */
    private void addRailGuards() {
        float halfWidth = Gdx.graphics.getWidth() / 2.0f;
        float halfHeight = Gdx.graphics.getHeight() / 2.0f;

        // Left Wall
        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.type = BodyDef.BodyType.KinematicBody;
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

    /**
     * Checks the contact of the player and a platform / wall, to see if they
     * match colours.
     * @param contact The contact object for the player and platform.
     */
    private void checkContact(Contact contact) {
        Body platformBody;

        // See which fixture is the platform.
        if (contact.getFixtureA().getBody().equals(player.getBody())) {
            platformBody = contact.getFixtureB().getBody();
        } else {
            platformBody = contact.getFixtureA().getBody();
        }

        Platform platform = (Platform)platformBody.getUserData();

        if (platform != null) {
            if (player.getColor().toFloatBits() != platform.getColor().toFloatBits()) {
                parent.gameOver(score);
            } else {
                if (!platform.isPointGained()) {
                    score++;
                    platform.setPointGained(true);
                }
            }
        }
    }
}
