package com.kegs.paintbouncer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends Sprite {

    // Fields
    private Body playerBody;

    public Player(World world) {
        super(new Texture(Gdx.files.internal("graphics/player.png")), 40, 40);

        // Set up Physics for the player.
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyType.DynamicBody;
        playerBodyDef.position.set(205, 600);

        playerBody = world.createBody(playerBodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(20.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.95f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.75f;

        Fixture fixture = playerBody.createFixture(fixtureDef);

        circle.dispose();
    }

    public void render(float delta) {
        
    }

    public void update(float delta) {
        setPosition(playerBody.getPosition().x - 20, playerBody.getPosition().y - 20);
    }
}
