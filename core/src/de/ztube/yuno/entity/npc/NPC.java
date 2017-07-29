package de.ztube.yuno.entity.npc;

import com.badlogic.gdx.assets.AssetManager;

import de.ztube.yuno.entity.Damageable;
import de.ztube.yuno.entity.Entity;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */
public class NPC extends Entity implements Damageable {

    public NPC(AssetManager assets) {
        super(assets);
    }

    @Override
    public void onDamaged() {

    }

    @Override
    public void dispose() {

    }
}
