package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Mats Svensson
 */
public class LoadingBar extends Actor {

    Animation animation;
    TextureRegion reg;
    float stateTime;

    public LoadingBar(Animation animation) {
        this.animation = animation;
        boolean looping = false;
		reg = animation.getKeyFrame(0, looping );
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        boolean looping = false;
        reg = animation.getKeyFrame(stateTime, looping);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
    	
        batch.draw(reg, getX(), getY());
    }
}
