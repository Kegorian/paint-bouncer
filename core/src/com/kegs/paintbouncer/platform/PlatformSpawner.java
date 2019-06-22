package com.kegs.paintbouncer.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import com.kegs.paintbouncer.colors.GameColors;

import java.util.ArrayList;
import java.util.Random;

public class PlatformSpawner {

    // Fields
    private Queue<Platform> platforms;
    private ArrayList<Texture> platformImages;
    private World world;
    private Random rnd;

    public PlatformSpawner(World world) {
        platforms = new Queue<Platform>(7);

        platformImages = new ArrayList<Texture>();
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_green.png")));
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_red.png")));
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_orange.png")));
        platformImages.add(new Texture(Gdx.files.internal("graphics/platforms/platform_blue.png")));

        this.world = world;
        rnd = new Random();

        spawnPlatform();
        spawnPlatform();
        spawnPlatform();

        System.out.println(platforms);
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

    public void spawnPlatform() {
        // Removes the platforms that are off the camera.
        if (platforms.size > 6) {
            platforms.removeFirst();
        }

        // Randomly get position.
        float x = rnd.nextInt(179) + 1.0f; // Between 1 and 180.
        float y;

        if (platforms.isEmpty()) {
            y = 700;
        } else {
            float yDis = rnd.nextInt(20) + 120.0f;
            y = platforms.last().getY() - yDis;
        }

        if (platforms.size % 2 == 0) {
            x = Gdx.graphics.getWidth() - x;
        }

        System.out.println("Platfrom added at x: " + x + ", y: " + y);

        int rndCol = rnd.nextInt(3);

        platforms.addLast(new Platform(world, platformImages.get(rndCol), x, y, GameColors.BLUE));
        platforms.last().update(0);
    }
}
