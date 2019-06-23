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

public class Platform extends Sprite {

    // Fields
    private Body body;
    private Color color;

    public Platform(World world, float x, float y, float rotation, Color color) {
        super(new Texture(Gdx.files.internal("graphics/platform.png")));

        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.type = BodyType.KinematicBody;
        platformBodyDef.position.set(new Vector2(x, y));

        body = world.createBody(platformBodyDef);

        PolygonShape platformShape = new PolygonShape();
        platformShape.setAsBox(300.0f, 10.0f);

        body.createFixture(platformShape, 0.0f);
        platformShape.dispose();

        // Set color
        this.color = color;

        body.setUserData(this);
        // Rotate body.
        setRotation(rotation);
        rotation = rotation * (float)(Math.PI/180);
        body.setTransform(body.getWorldCenter(), rotation);
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - 300.0f, body.getPosition().y - 10.0f);
    }

    public void rotate(float amount) {

    }

    public Color getColor() { return color; }
}
