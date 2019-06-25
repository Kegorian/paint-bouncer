package com.kegs.paintbouncer.input;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.kegs.paintbouncer.colors.GameColors;
import com.kegs.paintbouncer.entities.Player;

public class GameGestureListener implements GestureListener {

    // Fields
    private Player player;

    public GameGestureListener(Player player) {
        this.player = player;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        float tempX = velocityX;
        float tempY = velocityY;

        // All numbers need to be positive to make comparing them easier.
        if (tempX < 0) { tempX *= -1; }
        if (tempY < 0) { tempY *= -1; }

        if (tempX > tempY) {
            if (velocityX > 0)
                player.setPlayerColor(GameColors.BLUE);
            else
                player.setPlayerColor(GameColors.RED);
        } else {
            if (velocityY > 0)
                player.setPlayerColor(GameColors.GREEN);
            else
                player.setPlayerColor(GameColors.ORANGE);
        }

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
