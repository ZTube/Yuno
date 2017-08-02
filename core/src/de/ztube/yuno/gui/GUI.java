package de.ztube.yuno.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.ztube.yuno.Yuno;
import de.ztube.yuno.entity.player.Player;
import de.ztube.yuno.gui.heart.HeartContainer;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The whole GUI as a child of Stage*/
public class GUI extends Stage {
    private final AssetManager assets;

    //The HeartContainer
    private HeartContainer container;

    //The touchpad
    private Controls controls;

    private Player player;

    public GUI(AssetManager assets, Player player) {
        super(new FitViewport(Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT));

        this.assets = assets;
        this.player = player;

        //create a new HeartContainer
        container = new HeartContainer(assets, Player.getTotalHearts());

        //Some tests. TODO: remove later
        container.damage(7);
        container.heal(3);

        //Initialize the touchpad
        controls = new Controls(assets, player);

        //Add touchpad and HeartContainer to the stage
        addActor(controls);
        addActor(container);
    }

    @Override
    public void dispose() {
        super.dispose();
        container.dispose();
        controls.dispose();
    }

}
