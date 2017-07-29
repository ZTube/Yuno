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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.util.Calendar;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import de.ztube.yuno.Yuno;
import de.ztube.yuno.tween.SpriteAccessor;

/**
 * Created by ZTube on 17.07.2016.
 * Yuno
 */

/**The Splash screen*/
public class Splash implements Screen {

    private final Yuno yuno;
    private SpriteBatch batch;
    private TextField credits;
    private Sprite splash;

    //The BitmapFont to display the credits
    private BitmapFont splashFont;

    private boolean ranTween = false;

    private TweenManager tweenManager;

    public Splash(final Yuno yuno) {
        this.yuno = yuno;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splash.draw(batch);
        credits.draw(batch, splash.getColor().a);
        batch.end();

        tweenManager.update(delta);

        //Switch to MainMenu when all assets are loaded
        if (!ranTween && yuno.assets.update()) {
            ranTween = true;
            //Tween alpha interpolation
            Tween.to(splash, SpriteAccessor.ALPHA, 0.4f).target(0).delay(2f).setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    //Sets the new Screen
                    yuno.setScreen(new MainMenu(yuno));
                    dispose();
                }
            }).start(tweenManager);
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        //Load the splash background texture
        splash = new Sprite(new Texture("graphics/ui/splash/splash.jpg"));
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        //Load the ttf font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/splash.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        float multiplier = (float) Gdx.graphics.getHeight() / 1280f;
        parameter.size = Math.round(128f * multiplier);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = Math.round(1f * (multiplier < 1f ? 1f : multiplier + 0.6f));

        splashFont = generator.generateFont(parameter);
        //Dispose the generator
        generator.dispose();


        Integer date = Calendar.getInstance().get(Calendar.YEAR);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = splashFont;
        style.fontColor = Color.WHITE;


        credits = new TextField(String.format("©ZTube %d", date), style);
        credits.setAlignment(Align.center);
        credits.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 1f).target(1).delay(1f).start(tweenManager);

        yuno.assets.clear();
        loadGraphics();
        loadSounds();
        loadMaps();
        Gdx.app.log("Yuno", "loaded Splash");
    }

    //Load the graphical assets
    private void loadGraphics() {
        yuno.assets.load("graphics/entity/player/player.pack", TextureAtlas.class);
        yuno.assets.load("graphics/ui/game/icons.pack", TextureAtlas.class);
        for (FileHandle file : Gdx.files.internal("graphics/ui/menu/backgrounds/").list()) {
            if (!file.isDirectory())
                yuno.assets.load(file.path(), Texture.class);
        }

        for (FileHandle file : Gdx.files.internal("graphics/ui/menu/backgrounds/addons/").list()) {
            if (!file.isDirectory())
                yuno.assets.load(file.path(), Texture.class);
        }
    }

    //Load the soundfiles
    private void loadSounds() {
        for (FileHandle file : Gdx.files.internal("audio/music/menu/").list()) {
            if (!file.isDirectory())
                yuno.assets.load(file.path(), Music.class);
        }
        for (FileHandle file : Gdx.files.internal("audio/fx/").list()) {
            if (!file.isDirectory())
                yuno.assets.load(file.path(), Sound.class);
        }
    }

    //Load the maps
    private void loadMaps() {
        yuno.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        yuno.assets.load("maps/main.tmx", TiledMap.class);
        yuno.assets.load("maps/main2.tmx", TiledMap.class);
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();
    }
}
