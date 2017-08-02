package de.ztube.yuno.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by ZTube on 26.07.2016.
 * Yuno
 */

/**The Tween ActorAccessor used for alpha-fading*/
public class ActorAccessor implements TweenAccessor<Actor> {

    public static final int ALPHA = 0;

    //Get the Sprite's alpha
    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    //Set the Sprite's alpha
    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            default:
                assert false;
        }
    }
}
