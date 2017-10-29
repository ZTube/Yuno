package de.ztube.yuno.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.ztube.yuno.entity.Controllable;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The Class handling the touchpad's input*/
public class Touchpad extends com.badlogic.gdx.scenes.scene2d.ui.Touchpad {

    //Controllable entity
    private Controllable controllable;

    public Touchpad(Controllable controllable, Skin skin) {
        super(10, skin);
        this.controllable = controllable;

    }

    public void setControllable(Controllable controllable){
        this.controllable = controllable;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(controllable == null)
            return;

        //Updates the Players walkDirection
        if (getKnobPercentX() > 0.7f) {
            controllable.walkRight();
            //Log.v("YunoControls", "right");
        } else if (getKnobPercentX() < -0.7f) {
            controllable.walkLeft();
            //Log.v("YunoControls", "left");
        } else if (getKnobPercentY() > 0.7f) {
            controllable.walkUp();
            //Log.v("YunoControls", "up");
        } else if (getKnobPercentY() < -0.7f) {
            controllable.walkDown();
            //Log.v("YunoControls", "down");
        } else {
            controllable.standStill();
        }

    }
}
