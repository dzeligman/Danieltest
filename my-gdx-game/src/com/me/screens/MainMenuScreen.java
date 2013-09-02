package com.me.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.MyGdxGame;
import com.me.mygdxgame.PhysicsSystem;
import com.siondream.engine.gleed.Level;
import com.siondream.engine.gleed.LevelRenderer;

/**
 * @author Mats Svensson
 */
// todo change input processor somewhere else?
public class MainMenuScreen extends AbstractScreen implements GestureListener {

	 Music rainMusic;
	 Texture dropImage;
	 Texture bucketImage;
	 Sound dropSound;
	  
	 OrthographicCamera camera;
	 SpriteBatch batch;
	 Rectangle bucket;
	 Array<Rectangle> raindrops;
	 long lastDropTime;
	   LevelRenderer m_levelRenderer;
	 
	  Level m_level;
	  
	public static final float PIXELS_PER_METER = 100;
	  
	   
    public MainMenuScreen(MyGdxGame game) {
        super(game);
       
    }

    @Override
    public void render(float delta) {
    	
    	
	      // todo rework this
	      if (Gdx.input.isKeyPressed(Keys.UP)) {
				camera.position.y += 5.0f / PIXELS_PER_METER;
			}
			else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				camera.position.y -= 5.0f / PIXELS_PER_METER;
			}
			
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				camera.position.x += 5.0f / PIXELS_PER_METER;
			}
			else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				camera.position.x -= 5.0f / PIXELS_PER_METER;
			}
			
			if (Gdx.input.isKeyPressed(Keys.A)) {
				camera.zoom += 0.05f / PIXELS_PER_METER;
			}
			else if (Gdx.input.isKeyPressed(Keys.S)) {
				camera.zoom -= 0.05f / PIXELS_PER_METER;
			}
		
			camera.update();
    	
        // todo, this is for glee
        // Render all layers
	      m_levelRenderer.render(camera);
	      
	      // TODO, PHYSICS STEP
	      m_level.getPhysicsSystem().step(camera);
	     

    }
  
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    	
        Gdx.input.setInputProcessor(new GestureDetector(this));
        
    	// start the playback of the background music immediately
    	rainMusic = game.manager.get("rain.mp3", Music.class);
	  //  rainMusic.setLooping(true);
	    //rainMusic.play();
	    
    	camera = new OrthographicCamera(800 / PIXELS_PER_METER, 480 / PIXELS_PER_METER);
    	
    	/**
    	camera = new OrthographicCamera();  
        camera.viewportHeight = 320;  
        camera.viewportWidth = 480;  
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);  **/
  

	      	      // When it's ready, fetch the level and create renderer
	      m_level = game.manager.get("data/TestLevel.xml", Level.class);
	      m_levelRenderer = new LevelRenderer(m_level, null, 1);
	      m_level.getPhysicsSystem().initializeDebugRenderer();
   
    }

    @Override
    public void hide() {
    	// not sure if we dispose stuff here or need to clear the asset manager
    	rainMusic.setLooping(false);
    	
    }

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}
	// TODO may need to distinguish fling and pan actions
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		camera.position.add(-deltaX * camera.zoom / PIXELS_PER_METER, deltaY * camera.zoom / PIXELS_PER_METER, 0);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
    

}
