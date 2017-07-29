package de.ztube.yuno;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import de.ztube.yuno.screens.Splash;

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
    public AssetManager assets;

    @Override
    public void create() {
        //Initialize the AssetManager and start the Splashscreen
        assets = new AssetManager();
        setScreen(new Splash(this));

        Gdx.app.log("Yuno", "loaded Yuno");
    }

    @Override
    public void dispose() {
        super.dispose();
        assets.dispose();
    }
}
