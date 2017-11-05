package de.ztube.yuno.gui.heart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import static de.ztube.yuno.gui.heart.Heart.HeartState.FULL;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**Represents a Heart in the GUI*/
public class Heart extends Image {

    //The current HeartState
    private HeartState currentState;

    //The style
    private HeartStyle style;


    public Heart(Skin skin) {
        this(FULL, skin);
    }

    public Heart(HeartState state, Skin skin) {
        this(state, skin.get(HeartStyle.class));
    }

    public Heart(Skin skin, String styleName) {
        this(FULL, skin.get(styleName, HeartStyle.class));
    }

    public Heart(HeartState state, Skin skin, String styleName) {
        this(state, skin.get(styleName, HeartStyle.class));
    }

    public Heart(HeartStyle style) {
        this(FULL, style);
    }

    public Heart(HeartState state, HeartStyle style) {
        this.style = style;

        setState(state);

        Gdx.app.log("Yuno", "Heart created");

        this.currentState = state;
    }

    //Returns the HeartState
    public HeartState getState() {
        return currentState;
    }

    //Sets the HeartState
    public void setState(HeartState state) {
        currentState = state;


        switch (currentState){
            case FULL:
                setDrawable(style.full);
                break;
            case HALF:
                setDrawable(style.half);
                break;
            case EMPTY:
                setDrawable(style.empty);
                break;
        }
    }

    public static class HeartStyle{
        public Drawable empty, half, full;

        public HeartStyle(){

        }

        public HeartStyle(Drawable empty, Drawable half, Drawable full){
            this.empty = empty;
            this.half = half;
            this.full = full;
        }
    }

    //List of the different HeartStates
    public enum HeartState {
        EMPTY, HALF, FULL
    }
}
