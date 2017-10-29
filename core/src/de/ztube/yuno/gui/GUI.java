package de.ztube.yuno.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    //The HeartContainer
    private HeartContainer container;

    //The touchpad
    private Touchpad touchpad;

    private Table rootTable;

    public GUI(Player player) {
        super(new FitViewport(Yuno.SCREEN_WIDTH, Yuno.SCREEN_HEIGHT));

        Skin skin = Yuno.assets.get("graphics/ui/game/skin.json", Skin.class);

        //create a new HeartContainer
        container = new HeartContainer(3);

        //Some tests. TODO: remove later
        //container.damage(7);
        //container.heal(3);

        //Initialize the touchpad
        touchpad = new Touchpad(player, skin);

        //Add touchpad and HeartContainer to the stage
        rootTable = new Table();
        rootTable.setFillParent(true);
        addActor(rootTable);
        rootTable.setDebug(true);


        rootTable.add(container).expand().top().padTop(2);
        rootTable.row();
        rootTable.add(touchpad).bottom().left().padLeft(2).padBottom(2);
    }
}
