package com.kegs.paintbouncer.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import com.kegs.paintbouncer.colors.GameColors;

import java.util.Random;

/**
 * Holds a queue of platforms. Also allows the creation of new platforms and will
 * delete platforms that go off the screen.
 */
public class PlatformSpawner {

    // Fields
    private Queue<Platform> platforms;
    private World world;
    private Random rnd;
    private int platformCount;

    /**
     * Creates a new instance of PlatformSpawner.
     * @param world The current physics world.
     */
    public PlatformSpawner(World world) {
        platforms = new Queue<Platform>(8);

        this.world = world;
        rnd = new Random();
        platformCount = 0;

        spawnPlatform();
    }

    /**
     * Renders all platforms to the screen with their correct colour.
     */
    public void render(SpriteBatch spriteBatch) {
        for (Platform platform : platforms) {
            platform.setColor(platform.getColor());
            platform.draw(spriteBatch);
        }
    }

    /**
     * Updates the platforms.
     */
    public void update(float delta) {
        for (Platform platform : platforms) {
            platform.update(delta);
        }
    }

    public void dispose() {

    }

    /**
     * Spawns a new platform with a random distance from the one above. A random
     * rotation and a random length in towards the screen's centre. Also,
     * automatically detects which side the platform should be on, alternating
     * for every platform.
     */
    public void spawnPlatform() {
        // Randomly get position and colour.
        float x = rnd.nextInt(30) + 130;
        float y;
        Color prevCol;

        // Special case for the first platform spawn.
        if (platforms.isEmpty()) {
            y = 700;

            prevCol = GameColors.BLUE; // Just a colour to keep the method working.
        } else {
            float yDis = rnd.nextInt(10) + 200.0f;
            y = platforms.last().getY() - yDis;

            prevCol = platforms.last().getColor();
        }

        float rotation = (rnd.nextInt(16) + 18.0f) * -1;

        if (platformCount % 2 == 0) {
            x = 480 - x;
            rotation *= -1;
        }

        platforms.addLast(new Platform(world, x, y, rotation, getRndColor(prevCol)));
        platformCount++;
        platforms.last().update(0);
    }

    /**
     * @return The last platform in the list.
     */
    public Platform getLastPlatform() { return platforms.last(); }

    /**
     * Removes the first platform from the queue.
     */
    public void removePlatform() {
        if (platforms.size >= 7) {
            world.destroyBody(platforms.first().getBody());
            platforms.removeFirst();
        }
    }

    /**
     * Returns a random colours. Different from the colour entered as the
     * argument.
     */
    private Color getRndColor(Color prevCol) {
        Color newCol;

        switch(rnd.nextInt(4)) {
            case 0: newCol = GameColors.BLUE; break;
            case 1: newCol = GameColors.RED; break;
            case 2: newCol = GameColors.ORANGE; break;
            case 3: newCol = GameColors.GREEN; break;
            default: newCol = GameColors.ORANGE;
        }

        // To make sure that the player needs to change colour for every platform.
        // And there will not be a section where it is all just the same colour.
        if (prevCol.toFloatBits() == newCol.toFloatBits()) {
            return getRndColor(prevCol);
        } else {
            return newCol;
        }
    }
}
