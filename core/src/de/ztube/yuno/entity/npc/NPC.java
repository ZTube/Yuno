package de.ztube.yuno.entity.npc;

import de.ztube.yuno.entity.Damageable;
import de.ztube.yuno.entity.Entity;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The base class for an AI controlled Entity. Will be used in future*/
public class NPC extends Entity implements Damageable {

    public NPC() {

    }

    //Called when the NPC is damaged
    @Override
    public void onDamaged() {

    }

    @Override
    public void dispose() {

    }
}
