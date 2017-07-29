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
public class GUI {
    private final AssetManager assets;
    private HeartContainer container;
    private Controls controls;
    private Stage stage;
    private Player player;

    public GUI(AssetManager assets, Player player) {
        this.assets = assets;
        this.player = player;

        stage = new Stage(new ScalingViewport(Scaling.fit, Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        container = new HeartContainer(assets, Player.getTotalHearts());
        container.damage(7);
        container.heal(3);
        controls = new Controls(assets, player);

        stage.addActor(controls);
        stage.addActor(container);
    }

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
