package com.me.mygdxgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.siondream.engine.gleed.CircleElement;
import com.siondream.engine.gleed.RectangleElement;

public class PhysicsSystem {
	 World world;
	  // todo, ifdef debug
     Box2DDebugRenderer debugRenderer;
     
     static final float BOX_STEP=1/60f;  
     static final int BOX_VELOCITY_ITERATIONS=6;  
     static final int BOX_POSITION_ITERATIONS=2;  
     static final float WORLD_TO_BOX=0.01f;  
     static final float BOX_WORLD_TO=100f; 
     
     public PhysicsSystem()
     {
	 
    	 this.world = new World(new Vector2(0, -10), true); 
    	
     }
     
     public void initializeDebugRenderer()
     {
    	 this.debugRenderer = new Box2DDebugRenderer();
     }
     
     public void step( OrthographicCamera camera)
     {
    	 if (debugRenderer != null)
    		 debugRenderer.render(world, camera.combined);
	     world.step(1/60f, 6, 2);
	     
	      // TODO, LINK PHYSICS TO ACTUAL GAME OBJECTS
	      //body.setUserData(Object);
	      /**
	       * Iterator<Body> bi = world.getBodies();
                  
			while (bi.hasNext()){
			    Body b = bi.next();
			
			    // Get the bodies user data - in this example, our user 
			    // data is an instance of the Entity class
			    Entity e = (Entity) b.getUserData();
			
			    if (e != null) {
			        // Update the entities/sprites position and angle
			        e.setPosition(b.getPosition().x, b.getPosition().y);
			        // We need to convert our angle from radians to degrees
			        e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
			    }
			}
	       */
     }
     
     
     
     // TODO  redo this
     public void createStaticRectangle( RectangleElement element )
     {
    	   // First we create a body definition
         BodyDef bodyDef = new BodyDef();
         // We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
         bodyDef.type = BodyType.StaticBody;
         // Set our body's starting position in the world
         bodyDef.position.set(element.getRectangle().getX() * WORLD_TO_BOX,element.getRectangle().getY() * WORLD_TO_BOX);
         
         // Create our body in the world using our body definition
         Body body = world.createBody(bodyDef);
         
         // Create a polygon shape
         PolygonShape groundBox = new PolygonShape();  
         // Set the polygon shape as a box which is twice the size of our view port and 20 high
         // (setAsBox takes half-width and half-height as arguments)
         groundBox.setAsBox((element.getRectangle().getWidth()/2) * WORLD_TO_BOX, (element.getRectangle().getHeight()/2) * WORLD_TO_BOX);
         // Create a fixture from our polygon shape and add it to our ground body  
         body.createFixture(groundBox, 0.0f); 
         // Clean up after ourselves
         groundBox.dispose();
     
     }
     
     public void createDynamicRectangle( RectangleElement element )
     {
    	   // First we create a body definition
         BodyDef bodyDef = new BodyDef();
         // We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
         bodyDef.type = BodyType.DynamicBody;
         // Set our body's starting position in the world
         bodyDef.position.set(element.getRectangle().getX() * WORLD_TO_BOX,element.getRectangle().getY() * WORLD_TO_BOX);
         

         // Create our body in the world using our body definition
         Body body = world.createBody(bodyDef);
         // TODO user data
         //body.setUserData(entity);
         
         // Create a polygon shape
         PolygonShape groundBox = new PolygonShape();  
         // Set the polygon shape as a box which is twice the size of our view port and 20 high
         // (setAsBox takes half-width and half-height as arguments)
         groundBox.setAsBox((element.getRectangle().getWidth()/2) * WORLD_TO_BOX, (element.getRectangle().getHeight()/2) * WORLD_TO_BOX);
         // Create a fixture from our polygon shape and add it to our ground body  
         body.createFixture(groundBox, 0.0f); 
         // Clean up after ourselves
         groundBox.dispose();
     
     }
     
     public void createStaticCircle( CircleElement element )
     {
  	   // First we create a body definition
         BodyDef bodyDef = new BodyDef();
         // We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
         bodyDef.type = BodyType.StaticBody;
         // Set our body's starting position in the world
         bodyDef.position.set(element.getCircle().x * WORLD_TO_BOX,element.getCircle().y * WORLD_TO_BOX);
    	 
    	  // Create a circle shape and set its radius 
         CircleShape circle = new CircleShape();
         circle.setRadius(element.getCircle().radius * WORLD_TO_BOX);
         
         // Create a fixture definition to apply our shape to
         FixtureDef fixtureDef = new FixtureDef();
         fixtureDef.shape = circle;
         
         // Create our body in the world using our body definition
         Body body = world.createBody(bodyDef);
         // Create our fixture and attach it to the body
         Fixture fixture = body.createFixture(fixtureDef);
         
         // Remember to dispose of any shapes after you're done with them!
         // BodyDef and FixtureDef don't need disposing, but shapes do.
         circle.dispose();
     
     }
     
     public void createDynamicCircle( CircleElement element )
     {
  	   // First we create a body definition
         BodyDef bodyDef = new BodyDef();
         // We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
         bodyDef.type = BodyType.DynamicBody;
         // Set our body's starting position in the world
         bodyDef.position.set(element.getCircle().x * WORLD_TO_BOX,element.getCircle().y * WORLD_TO_BOX);
    	 
    	  // Create a circle shape and set its radius 
         CircleShape circle = new CircleShape();
         circle.setRadius(element.getCircle().radius * WORLD_TO_BOX);
         
    	  // Create a fixture definition to apply our shape to
         FixtureDef fixtureDef = new FixtureDef();
         fixtureDef.shape = circle;
         fixtureDef.density = 0.5f; 
         fixtureDef.friction = 0.4f;
         fixtureDef.restitution = 0.6f; // Make it bounce a little bit
         
         // Create our body in the world using our body definition
         Body body = world.createBody(bodyDef);
         // Create our fixture and attach it to the body
         Fixture fixture = body.createFixture(fixtureDef);
         
         // Remember to dispose of any shapes after you're done with them!
         // BodyDef and FixtureDef don't need disposing, but shapes do.
         circle.dispose();
     }
	

   
}
