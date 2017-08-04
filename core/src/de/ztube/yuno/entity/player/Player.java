package de.ztube.yuno.entity.player;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import de.ztube.yuno.Yuno;
import de.ztube.yuno.entity.Damageable;
import de.ztube.yuno.entity.Entity;
import de.ztube.yuno.exceptions.IllegalSpawnPositionException;
import de.ztube.yuno.screens.Game;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The Player sprite controlled by a touchpad*/
public class Player extends Entity implements Damageable {

    private static final int totalHearts = 4;
    private final Game game;
    private WalkDirection walkDirection;

    //The target tile coordinates for tile based walking
    private Vector2 targetTile;

    //The Player's textures
    private TextureAtlas playerTexture;
    private Animation walkUp, walkDown, walkRight, walkLeft;
    //The elapsed time needed for the walk animations
    private float elapsedTime;

    //The current map
    private TiledMap map;

    public Player(final Game game, TiledMap map) {
        this.game = game;
        this.map = map;

        //Get the TextureAtlas through the AssetManager
        playerTexture = Yuno.assets.get("graphics/entity/player/player.pack", TextureAtlas.class);

        //Set the Player's texture and size. TODO: better way?
        set(playerTexture.createSprite("walk.down"));

        //Initialize the walk animations
        walkUp = new Animation(1f / 8f, playerTexture.findRegions("walk.up"));
        walkDown = new Animation(1f / 8f, playerTexture.findRegions("walk.down"));
        walkRight = new Animation(1f / 8f, playerTexture.findRegions("walk.right"));
        walkLeft = new Animation(1f / 8f, playerTexture.findRegions("walk.left"));

        targetTile = new Vector2(-1, -1);
        walkDirection = WalkDirection.STILL;

        //Teleports the Player to the MapObject "spawnPlayer"
        setSpawnPosition("spawnPlayer");
    }

    //Returns the Player's total hearts
    public static int getTotalHearts() {
        return totalHearts;
    }

    //Used to display the Sprite
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();

        //UpdateMethods
        updateProperties(batch);
        updatePosition(Gdx.graphics.getDeltaTime());
        updateTexture();
    }

    private void updatePosition(float delta) {
        //Old position to reset if needed
        float oldX = getX();
        float oldY = getY();

        //Create Arrays of type MapObjects for doors and collision detection
        Array<MapObjects> collisionObjects = new Array<MapObjects>();
        Array<MapObject> doorObjects = new Array<MapObject>();

        //Tile based walking. TODO: bugfixing / improvement
        //Get the map's tile width and height
        int mapTileWidth = (int) ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();
        int mapTileHeight = (int) ((TiledMapTileLayer) map.getLayers().get(0)).getTileHeight();

        switch(walkDirection){
            case STILL:
                setVelocity(0, 0);
                break;
            case UP:
                setVelocity(0, getSpeed());
                targetTile.y = (int)Math.floor((double) getY() / (double) mapTileHeight) + 1;
                break;
            case DOWN:
                setVelocity(0, -getSpeed());
                targetTile.y = (int)Math.floor((double) getY() / (double) mapTileHeight);
                break;
            case RIGHT:
                setVelocity(getSpeed(), 0);
                targetTile.x = (int)Math.floor((double) getY() / (double) mapTileWidth) + 1;
                break;
            case LEFT:
                setVelocity(-getSpeed(), 0);
                targetTile.x = (int)Math.floor((double) getY() / (double) mapTileWidth);
                break;
        }

        //Update the Player's position
        setX(getX() + getVelocity().x * delta);
        setY(getY() + getVelocity().y * delta);

        //Iterate through all maplayers
        for (MapLayer layer : map.getLayers()) {

            //If the layers' name is "collision" add all objects to the array collisionObjects
            if (layer.getName().equalsIgnoreCase("collision")) {
                collisionObjects.add(layer.getObjects());
            }

            //If the layers' name is "doors" add all objects to the array doorObjects
            if (layer.getName().equalsIgnoreCase("doors")) {
                for (MapObject door : layer.getObjects()) {
                    doorObjects.add(door);

                    //If the door is locked add it to collisionObjects
                    MapObjects doorObject = new MapObjects();
                    if (door.getProperties().containsKey("locked") && door.getProperties().get("locked").equals("true")) {
                        doorObject.add(door);
                    }
                    collisionObjects.add(doorObject);
                }
            }
        }

        //Collision detection
        for (MapObjects collisionObject : collisionObjects) {

            for (RectangleMapObject rectangleObject : collisionObject.getByType(RectangleMapObject.class)) {

                Rectangle rectangle = rectangleObject.getRectangle();

                //Check if the Player's rectangle overlaps the collisionObject's rectangle
                if (getBoundingRectangle().overlaps(rectangle)) {
                    //Reset to old position
                    setX(oldX);
                    setY(oldY);
                }
            }
        }

        //Door handling
        for (MapObject door : doorObjects) {
            //Checks if the door is unlocked
            if (door.getProperties().containsKey("locked") && door.getProperties().get("locked").equals("false") && door.getProperties().containsKey("door")) {
                if (getBoundingRectangle().overlaps(((RectangleMapObject) door).getRectangle())) {

                    //Set the new map to the string provided by the door
                    setCurrentMap((String) door.getProperties().get("door"));
                    return;
                }
            }
        }
    }

    //Update the Player's texture
    private void updateTexture() {
        if (getVelocity().x == 0 && getVelocity().y == 0) {
            //Player standing still
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
            //Walk right animation
            setRegion(walkRight.getKeyFrame(elapsedTime, true));
        } else if (getVelocity().x < 0 && (getVelocity().y < 0.5f || getVelocity().y > -0.5f)) {
            //Walk left animation
            setRegion(walkLeft.getKeyFrame(elapsedTime, true));
        } else if (getVelocity().y > 0 && (getVelocity().x < 0.5f || getVelocity().x > -0.5f)) {
            //Walk up animation
            setRegion(walkUp.getKeyFrame(elapsedTime, true));
        } else if (getVelocity().y < 0 && (getVelocity().x < 0.5f || getVelocity().x > -0.5f)) {
            //Walk down animation
            setRegion(walkDown.getKeyFrame(elapsedTime, true));
        }
    }

    //Update the Player's properties
    private void updateProperties(Batch batch) {
    }

    //Called when the Player is damaged
    @Override
    public void onDamaged() {

    }

    //Update the Player's position
    private void setSpawnPosition(String spawn) {
        MapLayer spawnObjectLayer = null;

        //Get the layer with the name "Player"
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equalsIgnoreCase("Player")) {
                spawnObjectLayer = layer;
            }
        }

        //Check if spawnObjectLayer is set and "spawn" exists
        if (spawnObjectLayer != null && spawnObjectLayer.getObjects().get(spawn) != null) {
            MapObject spawnObject = spawnObjectLayer.getObjects().get(spawn);

            //Get the spawn coordinates from the spawnObject
            Float x = null;
            Float y = null;
            try {
                x = spawnObject.getProperties().get("x", Float.class);
                y = spawnObject.getProperties().get("y", Float.class);

            } catch (GdxRuntimeException e) {
                //Problem getting the coordinates
                throw new IllegalSpawnPositionException(x, y);
            }

            //Set the new position
            targetTile.x = -1;
            targetTile.y = -1;
            setPosition(x - getWidth() / 2, y - getHeight() / 2);
        }
        else{
            //Layer does not exist
            throw new IllegalSpawnPositionException(-1f, -1f);
        }
    }

    //Return the current walk direction
    public WalkDirection getWalkDirection() {
        return walkDirection;
    }

    //Set the current walk direction and update the corresponding velocity
    public void setWalkDirection(WalkDirection walkDirection) {
        this.walkDirection = walkDirection;
    }

    //Get the current map
    public TiledMap getCurrentMap() {
        return map;
    }

    //Update the current map
    public void setCurrentMap(String params) {
        //Split the String into two halfs
        String[] mapData = params.split(",");

        //Get the mapName and spawnName from the array mapData and replace all spaces
        String mapName = mapData[0].replace(" ", "");
        String spawnName = mapData[1].replace(" ", "");

        //if the mapName = "this" stay on the same map, else load the new map
        if (!mapName.equalsIgnoreCase("this")) {
            this.map = game.setMap("maps/" + mapName);
        }

        //Teleport the Player
        setSpawnPosition(spawnName);
    }

    @Override
    public void dispose() {

    }

    //The different walk directions
    public enum WalkDirection {
        STILL, UP, DOWN, RIGHT, LEFT
    }
}
