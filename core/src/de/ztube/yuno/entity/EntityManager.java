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
public class EntityManager implements Iterable<Entity>, Comparator<Entity> {

    private static EntityManager instance = new EntityManager();
    private ArrayList<Entity> entities;

    private EntityManager() {
        entities = new ArrayList<Entity>();
    }

    public static EntityManager getInstance() {
        return instance;
    }

    public void createEntity(Entity entity) {
        if (entity == null)
            throw new NullPointerException();

        entities.add(entity);
    }

    public Entity getEntity(UUID uuid) {
        for (Entity entity : this) {
            if (entity.getUUID().equals(uuid))
                return entity;
        }
        throw new EntityNotFoundException();
    }

    public void destroyEntity(UUID uuid) {
        for (Entity entity : this) {
            if (entity.getUUID().equals(uuid))
                entities.remove(entity);
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
