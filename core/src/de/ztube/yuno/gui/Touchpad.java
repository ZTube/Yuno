package de.ztube.yuno.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.ztube.yuno.entity.player.Player;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The Class handling the touchpad's input*/
public class Touchpad extends com.badlogic.gdx.scenes.scene2d.ui.Touchpad {

    //The Player
    private Player player;


    public Touchpad(Player player, Skin skin) {
        super(10, skin);
        this.player = player;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //Updates the Players walkDirection
        if (getKnobPercentX() > 0.7f) {
            player.setWalkDirection(Player.WalkDirection.RIGHT);
            //Log.v("YunoControls", "right");
        } else if (getKnobPercentX() < -0.7f) {
            player.setWalkDirection(Player.WalkDirection.LEFT);
            //Log.v("YunoControls", "left");
        } else if (getKnobPercentY() > 0.7f) {
            player.setWalkDirection(Player.WalkDirection.UP);
            //Log.v("YunoControls", "up");
        } else if (getKnobPercentY() < -0.7f) {
            player.setWalkDirection(Player.WalkDirection.DOWN);
            //Log.v("YunoControls", "down");
        } else {
            player.setWalkDirection(Player.WalkDirection.STILL);
        }

    }
}
