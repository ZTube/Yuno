package de.ztube.yuno;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import de.ztube.yuno.screens.Splash;

/**
 * Created by ZTube on 17.07.2016.
 * Yuno
 */
public class Yuno extends Game {

    public static final int SCREEN_WIDTH = 284;
    public static final int SCREEN_HEIGHT = 160;

    public AssetManager assets;

    @Override
    public void create() {
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
