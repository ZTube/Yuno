package de.ztube.yuno.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.ztube.yuno.Yuno;

public class DesktopLauncher {
    public static void main(String[] arg) {

        //DesktopLauncher for testing purposes only
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = false;
        config.title = "Yuno";
        config.useGL30 = true;

        //Window should be resizable for testing
        config.resizable = true;
        new LwjglApplication(new Yuno(), config);
    }
}
