package de.ztube.yuno.exceptions;

import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by ZTube on 25.07.2017.
 * Yuno
 */

/**General Exception*/
public class YunoException extends GdxRuntimeException {

    private static final String PREFIX = "Yuno: ";

    public YunoException() {
        super(PREFIX + "General YunoException, no information supplied");

    }

    public YunoException(String message) {
        super(PREFIX + message);
    }

    public YunoException(Throwable t) {
        super(t);
    }

    public YunoException(String message, Throwable t) {
        super(PREFIX + message, t);
    }
}
