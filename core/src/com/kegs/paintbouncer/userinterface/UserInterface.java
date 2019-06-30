package com.kegs.paintbouncer.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A class that represents a user interface in the game.
 */
public class UserInterface {

    // Fields
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    /**
     * Creates a new instance of UserInterface
     */
    public UserInterface() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(480, 800, camera);
        camera.setToOrtho(false, viewport.getWorldWidth(),
                viewport.getWorldHeight());
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("ui/KegUI.json"));
    }

    /**
     * Is called when the screen is resized.
     * @param width The width of the new screen.
     * @param height The height of the new screen.
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Renders the User Interface to the window.
     * @param delta Time between calls.
     */
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Adds an actor to the stage.
     */
    public void addActor(Actor actor) {
        stage.addActor(actor);
    }

    public Stage getStage() { return stage; }

    public Skin getSkin() { return skin; }
}
