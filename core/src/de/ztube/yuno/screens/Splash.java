package de.ztube.yuno.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Calendar;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import de.ztube.yuno.Yuno;
import de.ztube.yuno.tween.ActorAccessor;

/**
 * Created by ZTube on 17.07.2016.
 * Yuno
 */

/**The Splash screen*/
public class Splash implements Screen {

    private Stage stage;
    private TextField credits;
    private Image splash;

    //The BitmapFont to display the credits
    private BitmapFont splashFont;

    private boolean ranTween = false;

    private TweenManager tweenManager;

    public Splash() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        stage.act(delta);
        stage.draw();

        //Switch to MainMenu when all assets are loaded
        if (Yuno.assets.update() && !ranTween) {
            ranTween = true;
            //Tween alpha interpolation
            Tween.to(splash, ActorAccessor.ALPHA, 0.4f).target(0).delay(2f).setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    //Sets the new Screen
                    Yuno.screens.setScreen(new MainMenu());
                }
            }).start(tweenManager);
            Tween.to(credits, ActorAccessor.ALPHA, 0.4f).target(0).delay(2f).start(tweenManager);
        }
    }

    @Override
    public void show() {
        Viewport viewport = new FillViewport(Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT);

        stage = new Stage(viewport);

        //Load the splash background texture
        splash = new Image(new Texture("graphics/ui/splash/splash.jpg"));
        splash.setFillParent(true);

        //Load the ttf font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/splash.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1f;

        splashFont = generator.generateFont(parameter);
        //Dispose the generator
        generator.dispose();


        Integer date = Calendar.getInstance().get(Calendar.YEAR);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = splashFont;
        style.fontColor = Color.WHITE;

        credits = new TextField(String.format("©ZTube %d", date), style);
        credits.setAlignment(Align.center);
        credits.setSize(Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        Tween.set(splash, ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, ActorAccessor.ALPHA, 1f).target(1).delay(1f).start(tweenManager);

        Tween.set(credits, ActorAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(credits, ActorAccessor.ALPHA, 1f).target(1).delay(1f).start(tweenManager);

        stage.addActor(splash);
        stage.addActor(credits);


        Yuno.assets.clear();
        loadGraphics();
        loadSounds();
        loadMaps();
        Gdx.app.log("Yuno", "loaded Splash");
    }

    //Load the graphical assets
    private void loadGraphics() {
        Yuno.assets.load("graphics/entity/player/player.pack", TextureAtlas.class);
        Yuno.assets.load("graphics/ui/game/icons.pack", TextureAtlas.class);
        for (FileHandle file : Gdx.files.internal("graphics/ui/menu/backgrounds/").list()) {
            if (!file.isDirectory())
                Yuno.assets.load(file.path(), Texture.class);
        }

        for (FileHandle file : Gdx.files.internal("graphics/ui/menu/backgrounds/addons/").list()) {
            if (!file.isDirectory())
                Yuno.assets.load(file.path(), Texture.class);
        }
    }

    //Load the soundfiles
    private void loadSounds() {
        for (FileHandle file : Gdx.files.internal("audio/music/menu/").list()) {
            if (!file.isDirectory())
                Yuno.assets.load(file.path(), Music.class);
        }
        for (FileHandle file : Gdx.files.internal("audio/fx/").list()) {
            if (!file.isDirectory())
                Yuno.assets.load(file.path(), Sound.class);
        }
    }

    //Load the maps
    private void loadMaps() {
        Yuno.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        Yuno.assets.load("maps/main.tmx", TiledMap.class);
        Yuno.assets.load("maps/main2.tmx", TiledMap.class);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        stage.dispose();
    }
}
