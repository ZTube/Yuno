package de.ztube.yuno.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import de.ztube.yuno.entity.Damageable;
import de.ztube.yuno.entity.Entity;
import de.ztube.yuno.exceptions.IllegalSpawnPositionException;
import de.ztube.yuno.screens.Game;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */
public class Player extends Entity implements Damageable {

    private static final int totalHearts = 4;
    private final Game game;
    private WalkDirection walkDirection;
    private int targetTileX = -1;
    private int targetTileY = -1;

    private TextureAtlas playerTexture;
    private Animation walkUp, walkDown, walkRight, walkLeft;
    private float elapsedTime;
    private TiledMap map;

    public Player(AssetManager assets, final Game game, TiledMap map) {
        super(assets);
        this.game = game;
        this.map = map;


        playerTexture = assets.get("graphics/entity/player/player.pack", TextureAtlas.class);

        set(playerTexture.createSprite("walk.down"));

        walkUp = new Animation(1f / 8f, playerTexture.findRegions("walk.up"));
        walkDown = new Animation(1f / 8f, playerTexture.findRegions("walk.down"));
        walkRight = new Animation(1f / 8f, playerTexture.findRegions("walk.right"));
        walkLeft = new Animation(1f / 8f, playerTexture.findRegions("walk.left"));

        setSpawnPosition("spawnPlayer");
    }

    public static int getTotalHearts() {
        return totalHearts;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();

        updateProperties(batch);
        updatePosition(Gdx.graphics.getDeltaTime());
        updateTexture();
    }

    private void updatePosition(float delta) {
        float oldX = getX();
        float oldY = getY();

        Array<MapObjects> collisionObjects = new Array<MapObjects>();
        Array<MapObject> doorObjects = new Array<MapObject>();

        if (walkDirection == WalkDirection.STILL) {
            int mapTileWidth = (int) ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();
            int mapTileHeight = (int) ((TiledMapTileLayer) map.getLayers().get(0)).getTileHeight();

            if (getVelocity().x != 0) {
                if (targetTileX < 0 && getVelocity().x > 0)
                    targetTileX = (int) Math.floor((double) getX() / (double) mapTileWidth) + 1;
                else if (targetTileX < 0 && getVelocity().x < 0)
                    targetTileX = (int) Math.floor((double) getX() / (double) mapTileWidth);
                else if (getX() >= targetTileX * 16 && getVelocity().x > 0) {
                    setVelocityX(0);
                    setX(targetTileX * 16);
                    targetTileX = -1;
                } else if (getX() <= targetTileX * 16 && getVelocity().x < 0) {
                    setVelocityX(0);
                    setX(targetTileX * 16);
                    targetTileX = -1;
                }
            }

            if (getVelocity().y != 0) {
                if (targetTileY < 0 && getVelocity().y > 0)
                    targetTileY = (int) Math.floor((double) getY() / (double) mapTileHeight) + 1;
                else if (targetTileY < 0 && getVelocity().y < 0)
                    targetTileY = (int) Math.floor((double) getY() / (double) mapTileHeight);
                else if (getY() >= targetTileY * 16 && getVelocity().y > 0) {
                    setVelocityY(0);
                    setY(targetTileY * 16);
                    targetTileY = -1;
                } else if (getY() <= targetTileY * 16 && getVelocity().y < 0) {
                    setVelocityY(0);
                    setY(targetTileY * 16);
                    targetTileY = -1;
                }
            }
        }

        setX(getX() + getVelocity().x * delta);
        setY(getY() + getVelocity().y * delta);

        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equalsIgnoreCase("collision")) {
                collisionObjects.add(layer.getObjects());
            }


            if (layer.getName().equalsIgnoreCase("doors")) {
                for (MapObject door : layer.getObjects()) {
                    doorObjects.add(door);

                    MapObjects doorObject = new MapObjects();
                    if (door.getProperties().containsKey("locked") && door.getProperties().get("locked").equals("true")) {
                        doorObject.add(door);
                    }
                    collisionObjects.add(doorObject);
                }
            }
        }

        if (collisionObjects.size != 0)

        {

            for (MapObjects collisionObject : collisionObjects) {

                // there are several other types, Rectangle is probably the most common one
                for (RectangleMapObject rectangleObject : collisionObject.getByType(RectangleMapObject.class)) {

                    Rectangle rectangle = rectangleObject.getRectangle();
                    if (getBoundingRectangle().overlaps(rectangle)) {
                        // collision with object
                        setX(oldX);
                        setY(oldY);
                    }
                }
            }
        }

        for (
                MapObject door
                : doorObjects)

        {
            if (door.getProperties().containsKey("locked") && door.getProperties().get("locked").equals("false") && door.getProperties().containsKey("door")) {
                if (getBoundingRectangle().overlaps(((RectangleMapObject) door).getRectangle())) {

                    setCurrentMap((String) door.getProperties().get("door"));
                    return;
                }
            }
        }
    }

    private void updateTexture() {
        if (getVelocity().x == 0 && getVelocity().y == 0) {
            //stand still
            if (getRegionX() == walkUp.getKeyFrame(elapsedTime, true).getRegionX() && getRegionY() == walkUp.getKeyFrame(elapsedTime, true).getRegionY()) {
                setRegion(playerTexture.findRegion("stand.back"));
            }

            if (getRegionX() == walkDown.getKeyFrame(elapsedTime, true).getRegionX() && getRegionY() == walkDown.getKeyFrame(elapsedTime, true).getRegionY() || (getRegionY() == 0 && getRegionX() == 0)) {
                setRegion(playerTexture.findRegion("stand.front"));
            }

            if (getRegionX() == walkRight.getKeyFrame(elapsedTime, true).getRegionX() && getRegionY() == walkRight.getKeyFrame(elapsedTime, true).getRegionY()) {
                setRegion(playerTexture.findRegion("stand.right"));
            }

            if (getRegionX() == walkLeft.getKeyFrame(elapsedTime, true).getRegionX() && getRegionY() == walkLeft.getKeyFrame(elapsedTime, true).getRegionY()) {
                setRegion(playerTexture.findRegion("stand.left"));
            }
        } else if (getVelocity().x > 0 && (getVelocity().y < 0.5f || getVelocity().y > -0.5f)) {
            //walk right
            setRegion(walkRight.getKeyFrame(elapsedTime, true));
        } else if (getVelocity().x < 0 && (getVelocity().y < 0.5f || getVelocity().y > -0.5f)) {
            //walk left
            setRegion(walkLeft.getKeyFrame(elapsedTime, true));
        } else if (getVelocity().y > 0 && (getVelocity().x < 0.5f || getVelocity().x > -0.5f)) {
            //walk up
            setRegion(walkUp.getKeyFrame(elapsedTime, true));
        } else if (getVelocity().y < 0 && (getVelocity().x < 0.5f || getVelocity().x > -0.5f)) {
            //walk down
            setRegion(walkDown.getKeyFrame(elapsedTime, true));
        }
    }

    private void updateProperties(Batch batch) {
    }

    @Override
    public void onDamaged() {

    }

    private void setSpawnPosition(String spawn) {
        MapLayer spawnObjectLayer = null;
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equalsIgnoreCase("Player")) {
                spawnObjectLayer = layer;
            }
        }
        if (spawnObjectLayer != null && spawnObjectLayer.getObjects().get(spawn) != null) {
            MapObject spawnObject = spawnObjectLayer.getObjects().get(spawn);

            Float x = null;
            Float y = null;
            try {
                x = spawnObject.getProperties().get("x", Float.class);
                y = spawnObject.getProperties().get("y", Float.class);

            } catch (GdxRuntimeException e) {
                throw new IllegalSpawnPositionException(x, y);
            }

            setPosition(x - getWidth() / 2, y - getHeight() / 2);
        }
    }

    public WalkDirection getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(WalkDirection walkDirection) {
        this.walkDirection = walkDirection;

        switch (walkDirection) {
            case STILL:
                //Do Nothing
                break;
            case UP:
                setVelocityY(getSpeed());
                setVelocityX(0);
                break;
            case DOWN:
                setVelocityY(-getSpeed());
                setVelocityX(0);
                break;
            case RIGHT:
                setVelocityY(0);
                setVelocityX(getSpeed());
                break;
            case LEFT:
                setVelocityY(0);
                setVelocityX(-getSpeed());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public TiledMap getCurrentMap() {
        return map;
    }

    public void setCurrentMap(String params) {
        String[] mapData = params.split(",");

        String mapName = mapData[0].replace(" ", "");
        String spawnName = mapData[1].replace(" ", "");

        if (!mapName.equalsIgnoreCase("this")) {
            this.map = game.setMap("maps/" + mapName);
        }

        setSpawnPosition(spawnName);
    }

    @Override
    public void dispose() {

    }

    public enum WalkDirection {
        STILL, UP, RIGHT, DOWN, LEFT
    }
}
