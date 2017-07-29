package de.ztube.yuno;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.hideStatusBar = true;
        //config.useGLSurfaceView20API18 = true;
        config.useGL30 = true;
        config.hideStatusBar = true;
        //config.useImmersiveMode = true;
        config.useWakelock = true;

        initialize(new Yuno(), config);

    }
}
