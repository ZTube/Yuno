package de.ztube.yuno.exceptions;

import de.ztube.yuno.gui.heart.Heart;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */
public class IllegalHeartStateException extends YunoException {

    public IllegalHeartStateException() {
        super(String.format("HeartState can only be %s, %s or %s", Heart.HeartState.FULL.name(), Heart.HeartState.HALF.name(), Heart.HeartState.EMPTY.name()));
    }
}
