package com.kegs.paintbouncer.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A platform that the player will need to land on. This has a colour which the
 * player will need to match when it makes contact.
 */
public class Platform extends Sprite {

    // Fields
    private Body body;
    private Color color;
    private boolean pointGained;

    /**
     * Creates a new instance of Platform
     * @param world The current physics world.
     * @param x The world x position.
     * @param y The world y position.
     * @param rotation The rotation in degrees.
     * @param color The colour that the player has to match.
     */
    public Platform(World world, float x, float y, float rotation, Color color) {
        super(new Texture(Gdx.files.internal("graphics/platform.png")));
        // Scale the texture down to fit the physics body.
        setScale(0.5f);

        // Set up physics
        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.type = BodyType.KinematicBody;
        platformBodyDef.position.set(new Vector2(x, y));

        body = world.createBody(platformBodyDef);

        PolygonShape platformShape = new PolygonShape();
        platformShape.setAsBox(150.0f, 5.0f);

        body.createFixture(platformShape, 0.0f);
        platformShape.dispose();

        // Set color
        this.color = color;

        // When the ball hits this platform this is true.
        pointGained = false;

        // Attach the class to the physics body.
        body.setUserData(this);

        // Rotate body.
        setRotation(rotation);
        rotation = rotation * (float)(Math.PI/180);
        body.setTransform(body.getWorldCenter(), rotation);

        setPosition(body.getPosition().x - 300.0f, body.getPosition().y - 10.0f);
    }

    /**
     * Updates the position of the texture to match the physics body.
     */
    public void update(float delta) {
        setPosition(body.getPosition().x - 300.0f, body.getPosition().y - 10.0f);
    }

    public Body getBody() { return body; }

    public Color getColor() { return color; }

    public boolean isPointGained() { return pointGained; }

    public void setPointGained(boolean value) { pointGained = value; }
}
