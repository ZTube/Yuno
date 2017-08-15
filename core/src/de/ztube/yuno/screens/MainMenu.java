package de.ztube.yuno.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import de.ztube.yuno.Yuno;

/**
 * Created by ZTube on 17.07.2016.
 * Yuno
 */

/**The main Menu*/ //TODO: recreate
public class MainMenu implements Screen {

    public MainMenu() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {
        Gdx.app.log("Yuno", "loaded MainMenu");
        Yuno.screens.setScreen(new Game());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
