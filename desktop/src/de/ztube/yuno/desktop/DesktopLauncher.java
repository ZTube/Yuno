package de.ztube.yuno.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.ztube.yuno.Yuno;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = true;
        config.title = "Yuno";
        config.useGL30 = true;
        config.resizable = true;
        new LwjglApplication(new Yuno(), config);
    }
}
