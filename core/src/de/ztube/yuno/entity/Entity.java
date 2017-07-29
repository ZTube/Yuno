package de.ztube.yuno.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.UUID;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**Abstract class with base functionality for all Entities*/
public abstract class Entity extends Sprite implements Disposable, Comparable<Entity> {

    //The AssetManager used to load assets
    protected final AssetManager assets;

    //The players velocity used for movement
    private final Vector2 velocity;

    //The Unique Identification
    private final UUID uuid;

    //The current lifes
    private int lifes;

    //The movement speed
    private float speed = 60f * 1f;

    public Entity(AssetManager assets) {
        this.assets = assets;

        velocity = new Vector2(0, 0);

        //Generate a new random UUID
        uuid = UUID.randomUUID();
    }

    //Returns the Entity's lifes
    public final int getLifes() {
        return lifes;
    }

    //Returns the UUID
    public UUID getUUID() {
        return uuid;
    }

    //Sets the velocity in x direction
    protected void setVelocityX(float x) {
        velocity.set(x, velocity.y);
    }

    //Sets the velocity in y direction
    protected void setVelocityY(float y) {
        velocity.set(velocity.x, y);
    }

    //Sets the velocity
    protected void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    //Returns the velocity
    protected Vector2 getVelocity() {
        return velocity;
    }

    //Returns the current speed
    protected float getSpeed() {
        return speed;
    }

    //Compare to another Entity
    @Override
    public int compareTo(Entity entity) {
        //TODO: compare names instead of the UUID
        return getUUID().compareTo(entity.getUUID());
    }

    @Override
    public boolean equals(Object obj) {
        //If the UUIDs are the same, the entities are equal
        return getUUID().equals(obj);
    }

    @Override
    public String toString() {
        //Returns the UUID
        return getUUID().toString();
    }
}
