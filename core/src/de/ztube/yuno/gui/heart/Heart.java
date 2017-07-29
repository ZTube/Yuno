package de.ztube.yuno.gui.heart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.ztube.yuno.exceptions.IllegalHeartStateException;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */
public class Heart extends Sprite implements Disposable {
    private HeartState currentState = HeartState.FULL;
    private Array<Sprite> heartTextures;

    public Heart(AssetManager assets) {
        this(assets, HeartState.FULL);
    }

    public Heart(AssetManager assets, HeartState state) {
        TextureAtlas heartTexture = assets.get("graphics/ui/game/icons.pack", TextureAtlas.class);
        heartTextures = new Array<Sprite>(2);
        heartTextures = heartTexture.createSprites();
        heartTextures.reverse();

        //setTexture(heartTexture.getTextures().first());
        set(heartTextures.get(state.getTextureId()));
        Gdx.app.log("Yuno", "Heart created");

        this.currentState = state;
        updateTexture();
    }

    private void updateTexture() {

        if (currentState == HeartState.FULL) {
            Sprite sFull = heartTextures.get(HeartState.FULL.getTextureId());
            setRegion(sFull.getRegionX(), sFull.getRegionY(), sFull.getRegionWidth(), sFull.getRegionHeight());
        } else if (currentState == HeartState.HALF) {
            Sprite sHalf = heartTextures.get(HeartState.HALF.getTextureId());
            setRegion(sHalf.getRegionX(), sHalf.getRegionY(), sHalf.getRegionWidth(), sHalf.getRegionHeight());
        } else if (currentState == HeartState.EMPTY) {
            Sprite sEmpty = heartTextures.get(HeartState.EMPTY.getTextureId());
            setRegion(sEmpty.getRegionX(), sEmpty.getRegionY(), sEmpty.getRegionWidth(), sEmpty.getRegionHeight());
        } else {
            throw new IllegalHeartStateException();
        }
    }

    public HeartState getState() {
        return currentState;
    }

    public void setState(HeartState state) {
        currentState = state;
        updateTexture();
    }

    @Override
    public void dispose() {
        getTexture().dispose();
        for (Sprite sprite : heartTextures) {
            sprite.getTexture().dispose();
        }
    }

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
