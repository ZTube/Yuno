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
public abstract class Entity extends Sprite implements Disposable, Comparable<Entity> {

    protected final AssetManager assets;
    private final Vector2 velocity;
    private final UUID uuid;
    private int lifes;
    private float speed = 60f * 1f;

    public Entity(AssetManager assets) {
        this.assets = assets;

        velocity = new Vector2(0, 0);
        uuid = UUID.randomUUID();
    }

    public final int getLifes() {
        return lifes;
    }

    public UUID getUUID() {
        return uuid;
    }

    protected void setVelocityX(float x) {
        velocity.set(x, velocity.y);
    }

    protected void setVelocityY(float y) {
        velocity.set(velocity.x, y);
    }

    protected void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    protected Vector2 getVelocity() {
        return velocity;
    }

    protected float getSpeed() {
        return speed;
    }

    @Override
    public int compareTo(Entity entity) {
        return getUUID().compareTo(entity.getUUID());
    }

    @Override
    public boolean equals(Object obj) {
        return getUUID().equals(obj);
    }

    @Override
    public String toString() {
        return getUUID().toString();
    }
}
