package de.ztube.yuno.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.ztube.yuno.Yuno;
import de.ztube.yuno.entity.player.Player;
import de.ztube.yuno.gui.GUI;

/**
 * Created by ZTube on 17.07.2016.
 * Yuno
 */

/**The main Game class*/
public class Game implements Screen {

    private final AssetManager assets;

    //The SpriteBatch
    private SpriteBatch batch;

    //The camera
    private OrthographicCamera camera;

    //The viewport
    private Viewport viewport;

    //The current map and renderer
    private TiledMap map;
    private TiledMapRenderer renderer;

    //The Player
    private Player player;

    //Shader
    private ShaderProgram shader;

    //The GUI
    private GUI gui;


    private int mapWidth, mapHeight, mapTileWidth, mapTileHeight;

    //List of MapLayers which should be displayed either over, or under the player
    private IntArray renderOverPlayer = new IntArray();
    private IntArray renderUnderPlayer = new IntArray();

    public Game(AssetManager assets) {
        this.assets = assets;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set uniform "u_rand" to a random number for noise shading. TODO: better way?
        shader.begin();
        shader.setUniformf("u_rand", MathUtils.random(0f, 1f));
        shader.end();

        //Update the camera position according to the players position
        updateCameraPosition();

        camera.update();
        renderer.setView(camera);

        renderer.render(renderUnderPlayer.toArray());

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //Draw the Player
        player.draw(batch);
        batch.end();

        renderer.render(renderOverPlayer.toArray());

        //Draw the GUI
        gui.draw(delta);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT);
        viewport = new FitViewport(Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT, camera);


        camera.update();

        setMap("maps/main.tmx");

        player = new Player(assets, this, map);

        gui = new GUI(assets, player);

        ShaderProgram.pedantic = false;

        //Initialize the shader
        shader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"), Gdx.files.internal("shaders/vignette.fsh"));
        if (!shader.isCompiled())
            Gdx.app.error("Yuno", shader.getLog());

        batch.setShader(shader);

        shader.begin();
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        //In the night everything looks blueish and noisy

        //Day
        if (hour <= 18 && hour >= 6) {
            shader.setUniformf("u_time", 1.0f);
            shader.setUniformf("u_noise", 0.0f);
        }
        //Night
        else {
            shader.setUniformf("u_time", 0.3f);
            shader.setUniformf("u_noise", 0.4f);
        }
        shader.end();

        Gdx.app.log("Yuno", "loaded Game");
    }

    private void updateCameraPosition() {
        //Camera follows the Player
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 1);


        //Camera stops following the Player if he reaches the end of the map

        //OutTop
        if (player.getY() + player.getHeight() / 2 > mapTileHeight * mapHeight - camera.viewportHeight / 2) {
            camera.position.set(camera.position.x, mapTileHeight * mapHeight - camera.viewportHeight / 2, 1);
        }
        //OutBottom
        else if (player.getY() + player.getHeight() / 2 < camera.viewportHeight / 2) {
            camera.position.set(camera.position.x, camera.viewportHeight / 2, 1);
        }

        //OutLeft
        if (player.getX() + player.getWidth() / 2 < camera.viewportWidth / 2) {
            camera.position.set(camera.viewportWidth / 2, camera.position.y, 1);
        }
        //OutRight
        else if (player.getX() + player.getWidth() / 2 > mapTileWidth * mapWidth - camera.viewportWidth / 2) {
            camera.position.set(mapTileWidth * mapWidth - camera.viewportWidth / 2, camera.position.y, 1);
        }
    }


    //Update the map returning itself
    public TiledMap setMap(String mapPath) {
        map = assets.get(mapPath, TiledMap.class);

        mapWidth = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth();
        mapHeight = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight();
        mapTileWidth = (int) ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();
        mapTileHeight = (int) ((TiledMapTileLayer) map.getLayers().get(0)).getTileHeight();

        renderer = new OrthogonalTiledMapRenderer(map, batch);
        renderer.setView(camera);

        renderOverPlayer.clear();
        renderUnderPlayer.clear();

        for (int i = 0; i < map.getLayers().getCount(); i++) {
            MapLayer layer = map.getLayers().get(i);

            //assign mapLayers to being rendered over or under the Player
            if (layer.getProperties().containsKey("floating") && layer.getProperties().get("floating").equals("true"))
                renderOverPlayer.add(i);
            else
                renderUnderPlayer.add(i);

        }
        return map;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        map.dispose();
        batch.dispose();
        gui.dispose();
        shader.dispose();
    }
}
