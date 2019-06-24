package com.kegs.paintbouncer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kegs.paintbouncer.colors.GameColors;
import com.kegs.paintbouncer.platform.Platform;

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

        // Set up Physics for the player.
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyType.DynamicBody;
        playerBodyDef.position.set((Gdx.graphics.getWidth() - getWidth()) / 2.0f, 775);

        playerBody = world.createBody(playerBodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(10.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.7f;
        fixtureDef.friction = 0.1f;
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

    /**
     * Checks the contact of the player and a platform / wall, to see if they
     * match colours.
     * @param contact The contact object for the player and platform.
     */
    public void checkContact(Contact contact) {
        Body platform;

        // See which fixture is the platform.
        if (contact.getFixtureA().getBody().equals(playerBody)) {
            platform = contact.getFixtureB().getBody();
        } else {
            platform = contact.getFixtureA().getBody();
        }

        if (platform.getUserData() != null) {
            if (color.toFloatBits() != ((Platform)platform.getUserData()).getColor().toFloatBits()) {
                // TODO: Add something when the colours and not the same.
            }
        }
    }

    public Color getColor() { return color; }
}
