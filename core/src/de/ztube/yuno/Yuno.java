package de.ztube.yuno;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import de.ztube.yuno.screens.Splash;
import de.ztube.yuno.screens.utils.ScreenManager;

/**
 * Created by ZTube on 17.07.2016.
 * Yuno
 */

/**The main class*/
public class Yuno extends Game {

    //The screen width and height. Size is the same as a Gameboy Advance
    public static final int SCREEN_WIDTH = 284;
    public static final int SCREEN_HEIGHT = 160;

    //The AssetManager used for caching assets to load them quicker
    public static AssetManager assets;
    public static ScreenManager screens;

    @Override
    public void create() {
        //Initialize the AssetManager and start the Splashscreen
        assets = new AssetManager();
        screens = new ScreenManager(this);

        Gdx.app.log("Yuno", "loaded Yuno");
        screens.setScreen(new Splash());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        assets.dispose();
    }
}
