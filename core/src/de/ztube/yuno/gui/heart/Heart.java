package de.ztube.yuno.gui.heart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

import de.ztube.yuno.Yuno;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**Represents a Heart in the GUI*/
public class Heart extends Image {

    //The current HeartState
    private HeartState currentState;

    //The textures
    private Array<Sprite> heartTextures;

    public Heart() {
        this(HeartState.FULL);
    }

    public Heart(HeartState state) {
        TextureAtlas heartTextureAtlas = Yuno.assets.get("graphics/ui/game/icons.pack", TextureAtlas.class);
        heartTextures = new Array<Sprite>(2);
        heartTextures = heartTextureAtlas.createSprites();
        heartTextures.reverse();

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

        setDrawable(new SpriteDrawable(heartTextures.get(state.getTextureId())));
        setSize(heartTextures.get(state.getTextureId()).getWidth(), heartTextures.get(state.getTextureId()).getHeight());
    }

    //List of the different HeartStates
    public enum HeartState {
        EMPTY(0),
        HALF(1),
        FULL(2);

        private final int textureId;

        HeartState(int textureId) {
            this.textureId = textureId;
        }

        public int getTextureId() {
            return textureId;
        }
    }
}
