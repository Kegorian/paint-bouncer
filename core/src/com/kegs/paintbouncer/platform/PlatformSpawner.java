package com.kegs.paintbouncer.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class PlatformSpawner {

    // Fields
    private ArrayList<Platform> platforms;
    private ArrayList<Texture> platformImages;

    public PlatformSpawner(World world) {
        platforms = new ArrayList<Platform>();

        platformImages = new ArrayList<Texture>();
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_green.png")));
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_red.png")));
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_orange.png")));
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_blue.png")));

        platforms.add(new Platform(world, platformImages.get(0), 250, 200));
    }

    public void render(SpriteBatch spriteBatch) {
        for (Platform platform : platforms) {
            spriteBatch.draw(platform, platform.getX(), platform.getY());
        }
    }

    public void update(float delta) {
        for (Platform platform : platforms) {
            platform.update(delta);
        }
    }

    public void dispose() {
        for (Texture image : platformImages) {
            image.dispose();
        }
    }
}
