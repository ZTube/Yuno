package de.ztube.yuno.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import de.ztube.yuno.Yuno;
import de.ztube.yuno.entity.player.Player;
import de.ztube.yuno.gui.heart.HeartContainer;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The whole GUI handling the touchpad and the HeartContainer*/
public class GUI {
    private final AssetManager assets;

    //The HeartContainer
    private HeartContainer container;

    //The touchpad
    private Controls controls;

    //The scene2d stage
    private Stage stage;
    private Player player;

    public GUI(AssetManager assets, Player player) {
        this.assets = assets;
        this.player = player;

        //Initialize the stage
        stage = new Stage(new ScalingViewport(Scaling.fit, Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT));

        //Set the inputProcessor to stage
        Gdx.input.setInputProcessor(stage);

        //create a new HeartContainer
        container = new HeartContainer(assets, Player.getTotalHearts());

        //Some tests. TODO: remove later
        container.damage(7);
        container.heal(3);

        //Initialize the touchpad
        controls = new Controls(assets, player);

        //Add touchpad and HeartContainer to the stage
        stage.addActor(controls);
        stage.addActor(container);
    }

    //Draw the stage
    public void draw(float delta) {

        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        container.dispose();
        controls.dispose();
        stage.dispose();
    }

}
