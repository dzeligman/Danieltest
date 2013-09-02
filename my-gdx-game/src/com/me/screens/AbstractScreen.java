package com.me.screens;

import com.badlogic.gdx.Screen;
import com.me.mygdxgame.MyGdxGame;

/**
 * @author Mats Svensson
 */
public abstract class AbstractScreen implements Screen {

    protected MyGdxGame game;

    public AbstractScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
