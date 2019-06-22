package com.kegs.paintbouncer.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends Sprite {

    // Fields
    private Body body;

    public Platform(World world, Texture texture, float x, float y) {
        super(texture);

        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.position.set(new Vector2(x, y));

        body = world.createBody(platformBodyDef);

        PolygonShape platformShape = new PolygonShape();
        platformShape.setAsBox(200.0f, 10.0f);

        body.createFixture(platformShape, 0.0f);

        platformShape.dispose();
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - 200.0f, body.getPosition().y - 10.0f);
    }
}
