package de.ztube.yuno.exceptions;

/**
 * Created by ZTube on 25.07.2017.
 * Yuno
 */

public class IllegalSpawnPositionException extends YunoException {

    public IllegalSpawnPositionException(float x, float y) {
        super(String.format("Can't fetch Spawn Position Data from TiledMap with x: %s and y: %s", x, y));
    }
}
