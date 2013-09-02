package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.me.screens.LoadingScreen;
import com.siondream.engine.gleed.Level;
import com.siondream.engine.gleed.LevelRenderer;


public class MyGdxGame extends Game  {

	   public AssetManager manager = new AssetManager();
	   Level m_level;
	   
		
		   
	@Override
	public void create() {	
		
		setScreen(new LoadingScreen(this));  
	}

	 

	@Override
	public void dispose() {
		manager.clear();
		//manager.dispose();// or do .clear to get rid of all assets, otherwise we have to do .unload

	      }

}
