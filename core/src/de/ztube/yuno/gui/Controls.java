package de.ztube.yuno.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import de.ztube.yuno.entity.player.Player;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The Class handling the touchpad's input*/
public class Controls extends Actor {

    //The Touchpad and style
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground, touchKnob;

    //The Player
    private Player player;


    public Controls(Player player) {
        this.player = player;

        //Initialize the touchpad and set its style
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("graphics/ui/game/touchpad2.png"));

        touchpadSkin.add("touchKnob", new Texture("graphics/ui/game/touchknob.jpg"));
        touchpadStyle = new Touchpad.TouchpadStyle();

        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(10, touchpadStyle);

        touchpad.setPosition(4, 4);

    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return touchpad.hit(x, y, touchable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        //Updates the Players walkDirection
        if (touchpad.getKnobPercentX() > 0.7f) {
            player.setWalkDirection(Player.WalkDirection.RIGHT);
            //Log.v("YunoControls", "right");
        } else if (touchpad.getKnobPercentX() < -0.7f) {
            player.setWalkDirection(Player.WalkDirection.LEFT);
            //Log.v("YunoControls", "left");
        } else if (touchpad.getKnobPercentY() > 0.7f) {
            player.setWalkDirection(Player.WalkDirection.UP);
            //Log.v("YunoControls", "up");
        } else if (touchpad.getKnobPercentY() < -0.7f) {
            player.setWalkDirection(Player.WalkDirection.DOWN);
            //Log.v("YunoControls", "down");
        } else {
            player.setWalkDirection(Player.WalkDirection.STILL);
        }

        //Sets the touchpad's alpha
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        touchpad.draw(batch, 1);

    }

    public void dispose() {
        touchpadSkin.dispose();
    }
}
