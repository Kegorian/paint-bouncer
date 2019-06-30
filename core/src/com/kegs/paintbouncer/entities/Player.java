package com.kegs.paintbouncer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kegs.paintbouncer.colors.GameColors;

import java.util.Random;

/**
 * The player. The user will have no direct control over the movement of the
 * physics body, however they will be able to change the colour of the texture.
 * For the game's mechanics.
 */
public class Player extends Sprite {

    // Fields
    private Body playerBody;
    private Color color;

    /**
     * Creates a new instance of Player.
     * @param world The current physics world.
     */
    public Player(World world) {
        super(new Texture(Gdx.files.internal("graphics/player.png")), 40, 40);

        // Scale the texture down to fit the physics body.
        setScale(0.5f);

        // Random X Spawn.
        Random rnd = new Random();
        float xPos = ((480 - getWidth()) / 2.0f) - 80;
        float randX = rnd.nextInt(160);

        // Set up Physics for the player.
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyType.DynamicBody;
        playerBodyDef.position.set(xPos + randX, 820);

        playerBody = world.createBody(playerBodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(10.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.7f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.95f;

        playerBody.createFixture(fixtureDef);

        circle.dispose();

        // Default Color.
        color = GameColors.BLUE;
    }

    /**
     * Updates the position of the texture to match the physics body.
     */
    public void update(float delta) {
        setPosition(playerBody.getPosition().x - 20, playerBody.getPosition().y - 20);
    }

    public void setPlayerColor(Color color) { this.color = color; }

    public Color getColor() { return color; }

    public Body getBody() { return playerBody; }
}
