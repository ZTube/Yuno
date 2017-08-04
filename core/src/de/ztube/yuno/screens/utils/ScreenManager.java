package de.ztube.yuno.screens.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.Stack;

import de.ztube.yuno.exceptions.YunoException;

/**
 * Created by ZTube on 04.08.2017.
 * Yuno
 */

public class ScreenManager {

    private final Stack<Screen> screens;
    private final Game game;

    public ScreenManager(Game game){
        this.game = game;
        screens = new Stack<Screen>();
    }

    public void setScreen(Screen screen){
        screens.push(screen);
        game.setScreen(screen);
    }

    public Screen getCurrentScreen(){
        if(!game.getScreen().equals(screens.peek()))
            throw new YunoException();

        return screens.peek();
    }

    public void endScreen(){
        game.getScreen().dispose();
    }
}
