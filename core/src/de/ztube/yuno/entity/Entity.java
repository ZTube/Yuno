package de.ztube.yuno.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.UUID;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**Abstract class with base functionality for all Entities*/
public abstract class Entity extends Sprite implements Disposable, Comparable<Entity>, Controllable {

    //The Entity's velocity used for movement
    private final Vector2 velocity;

    //The Unique Identification
    private final UUID uuid;

    //The current lifes
    private int lifes;

    //The movement speed
    private float speed = 60f * 1f;

    public Entity() {
        velocity = new Vector2(0, 0);

        //Generate a new random UUID
        uuid = UUID.randomUUID();
    }

    //Returns the Entity's lifes
    public final int getLifes() {
        return lifes;
    }

    //Returns the Entity's name based on the class name
    public String getName(){
        return getClass().getSimpleName();
    }

    //Returns the UUID
    public UUID getUUID() {
        return uuid;
    }

    //Sets the velocity in x direction
    protected void setVelocityX(float x) {
        velocity.x = x;
    }

    //Sets the velocity in y direction
    protected void setVelocityY(float y) {
        velocity.y = y;
    }

    //Sets the velocity
    protected void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
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
        return getName().compareTo(entity.getName()) + uuid.compareTo(entity.getUUID());
    }

    @Override
    public boolean equals(Object obj) {
        //Two Entities are equal if the names and UUIDs are equal
        return getName().equals(obj) && uuid.equals(obj);
    }

    @Override
    public int hashCode() {
        int tmpHash = 17;
        tmpHash = 31 * tmpHash + getName().hashCode();
        tmpHash = 31 * tmpHash + getUUID().hashCode();
        return tmpHash;
    }

    @Override
    public String toString() {
        //Returns the Name:UUID
        return String.format("%s:%s", getName(), uuid.toString());
    }
}
