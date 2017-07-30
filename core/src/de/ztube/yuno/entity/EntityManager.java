package de.ztube.yuno.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.UUID;

import de.ztube.yuno.exceptions.EntityNotFoundException;

/**
 * Created by ZTube on 29.07.2017.
 * Yuno
 */

/**Singleton for managing all Entitys*/
public class EntityManager implements Iterable<Entity>, Comparator<Entity> {

    private static EntityManager instance = new EntityManager();

    //The Entity ArrayList
    private ArrayList<Entity> entities;

    private EntityManager() {
        entities = new ArrayList<Entity>();
    }

    //The Singleton instance
    public static EntityManager getInstance() {
        return instance;
    }

    //Add an Entity to the List
    public void createEntity(Entity entity) {
        if (entity == null)
            throw new NullPointerException();

        entities.add(entity);
    }

    //Get the Entity with given UUID
    public Entity getEntity(UUID uuid) {
        for (Entity entity : this) {
            if (entity.getUUID().equals(uuid))
                return entity;
        }
        throw new EntityNotFoundException();
    }

    //Remove the Entity with given UUID
    public void destroyEntity(UUID uuid) {
        for (Entity entity : this) {
            if (entity.getUUID().equals(uuid)) {
                entities.remove(entity);
                entity.dispose();
            }
        }
    }

    public void destroyEntity(Entity entity) {
        destroyEntity(entity.getUUID());
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    @Override
    public int compare(Entity entity1, Entity entity2) {
        return entity1.compareTo(entity2);
    }
}
