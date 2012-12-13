package com.pearson.lagp.v3;

import java.util.Arrays;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.builder.BlackPawnTextureBuilder;
import org.anddev.andengine.opengl.texture.builder.ITextureBuilder.TextureSourcePackingException;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.ease.EaseQuadOut;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class Level1Activity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static final int ROAD_START = -120;
	private String tag = "Level1Activity";

	// ===========================================================
	// Fields
	// ===========================================================

	private Handler mHandler;
	
	protected Camera mCamera;

	protected Scene mMainScene;

	private Texture mBackgroundTexture;
	private Texture mTruckBlueTexture;
	private Texture mCarRedTexture;
	private Texture mCarBlueTexture;
	private Texture mWormTexture;
	private Texture mMyChickenTexture;
	private Texture mControlTexture;
	private Texture mKnobTexture;
	
	private Texture mTruckRedTexture;

	private TextureRegion mBackgroundTextureRegion;
	private TextureRegion mBulletTextureRegion;
	private TextureRegion mCrossTextureRegion;
	private TextureRegion mHatchetTextureRegion;
	private TiledTextureRegion mTruckBlueTextureRegion;
	private TiledTextureRegion mTruckRedTextureRegion;
	private TiledTextureRegion mCarRedTextureRegion;
	private TiledTextureRegion mCarBlueTextureRegion;
	private TiledTextureRegion mWormTextureRegion;
	private TiledTextureRegion mMyChickenTextureRegion;
	private TiledTextureRegion mControlTextureRegion;
	private TiledTextureRegion mKnobTextureRegion;
	
	private AnimatedSprite[] vehicle1 = new AnimatedSprite[500];
	private AnimatedSprite[] vehicle2 = new AnimatedSprite[500];
	private AnimatedSprite[] vehicle3= new AnimatedSprite[500];
	private AnimatedSprite[] vehicle4 = new AnimatedSprite[500];
	private AnimatedSprite[] worm = new AnimatedSprite[500];
	private AnimatedSprite[] myChicken = new AnimatedSprite[3];
	private AnimatedSprite Control;
	private AnimatedSprite Knob;
	
	private int nDanger1;
	private int nDanger2;
	private int nDanger3;
	private int nDanger4;
	private int nfood;
	private int nChicken;
	private int nControl;
	private int nKnob;
	Random gen;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		mHandler = new Handler();
		gen = new Random();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		/* Load Textures. */
		TextureRegionFactory.setAssetBasePath("gfx/Level1/");
		mBackgroundTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "level1bk.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mBackgroundTexture);
		
	
		
		mTruckBlueTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTruckBlueTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTruckBlueTexture, this, "truckBlueLeft.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mTruckBlueTexture);
		
		
		mTruckRedTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTruckRedTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTruckRedTexture, this, "truckRedLeft.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mTruckRedTexture);
		
		mCarRedTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mCarRedTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mCarRedTexture, this, "carRed.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mCarRedTexture);
		
		mCarBlueTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mCarBlueTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mCarBlueTexture, this, "carBlue.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mCarBlueTexture);
		
		mWormTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mWormTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mWormTexture, this, "worm1.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mWormTexture);
		
		
		mMyChickenTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mMyChickenTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mMyChickenTexture, this, "myChicken.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mMyChickenTexture);
		
		mControlTexture = new Texture(256, 128, TextureOptions.DEFAULT);
		mControlTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mControlTexture, this, "onscreen_control_base.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mControlTexture);
		
		mKnobTexture = new Texture(256, 128, TextureOptions.DEFAULT);
		mKnobTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mKnobTexture, this, "onscreen_control_knob.png", 128, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mKnobTexture);
		
	}
	
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);

		/* Center the camera. */
		final int centerX = (CAMERA_WIDTH - mBackgroundTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - mBackgroundTextureRegion.getHeight()) / 2;

		/* Create the sprites and add them to the scene. */
		final Sprite background = new Sprite(centerX, centerY, mBackgroundTextureRegion);
		scene.getLastChild().attachChild(background);

	

		
       	// Add first Dangerire (which will add the others)
       	nDanger1 = 0;
    	nDanger2 = 0;
    	nDanger3 = 0;
    	nDanger4 = 0;
    	nfood=0;
    	nChicken=0;
    	nControl=0;
    	nKnob=0;
		mHandler.postDelayed(mStartVehicle1,1000);
		mHandler.postDelayed(mStartVehicle2,1000);
		mHandler.postDelayed(mStartVehicle3,1000);
		mHandler.postDelayed(mStartVehicle4,1000);
		mHandler.postDelayed(mStartFood,1000);
		mHandler.postDelayed(mStartChicken,1000);
		mHandler.postDelayed(mStartControl,1000);
		mHandler.postDelayed(mStartKnob,1000);
		return scene;
	}

	@Override
	public void onLoadComplete() {
	}
	
    private Runnable mStartVehicle1 = new Runnable() {
        public void run() {
        	int i1 = nDanger1++;
        	Scene scene = Level1Activity.this.mEngine.getScene();
           	float startY = 64f;
           	vehicle1[i1] = new AnimatedSprite(CAMERA_WIDTH - 30.0f, startY, mTruckBlueTextureRegion.clone());
        	final long[] frameDurations = new long[26];
        	Arrays.fill(frameDurations, 1);
            vehicle1[i1].animate(frameDurations, 0, 25, true);
           	vehicle1[i1].registerEntityModifier(
           			new SequenceEntityModifier (
           						new AlphaModifier(1.0f, 0.0f, 1.0f),
          						new MoveModifier(3.0f, vehicle1[i1].getX(), -200.0f, 
         							vehicle1[i1].getY(), startY)));
           	scene.getLastChild().attachChild(vehicle1[i1]);
         	if (nDanger1 < 500){
         		Random ranTime = new Random();
        		
        			int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	
						
         		mHandler.postDelayed(mStartVehicle1,2000*Randomnumber);
         	}
         }
      };
     
     private Runnable mStartVehicle2 = new Runnable() {
         public void run() {
         	int i2 = nDanger2++;
         	Scene scene = Level1Activity.this.mEngine.getScene();
            	float startY = 111f;
            	vehicle2[i2] = new AnimatedSprite(CAMERA_WIDTH - 30.0f, startY, mTruckRedTextureRegion.clone());
         	final long[] frameDurations = new long[26];
         	Arrays.fill(frameDurations, 1);
             vehicle2[i2].animate(frameDurations, 0, 25, true);
            	vehicle2[i2].registerEntityModifier(
            			new SequenceEntityModifier (
            						new AlphaModifier(1.0f, 0.0f, 1.0f),
           						new MoveModifier(3.0f, vehicle2[i2].getX(), -300.0f, 
          							vehicle2[i2].getY(), startY)));
            	scene.getLastChild().attachChild(vehicle2[i2]);
         	if (nDanger2 < 500){
         		Random ranTime = new Random();
        		
        			int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	
						
         		mHandler.postDelayed(mStartVehicle2,2000*Randomnumber);
         	}
         }
      };
      
      private Runnable mStartVehicle3 = new Runnable() {
          public void run() {
          	int i3 = nDanger3++;
          	Scene scene = Level1Activity.this.mEngine.getScene();
             	float startY = 155;
             	vehicle3[i3] = new AnimatedSprite(ROAD_START + 30.0f, startY, mCarRedTextureRegion.clone());
          	final long[] frameDurations = new long[26];
          	Arrays.fill(frameDurations, 1);
              vehicle3[i3].animate(frameDurations, 0, 25, true);
             	vehicle3[i3].registerEntityModifier(
             			new SequenceEntityModifier (
             						new AlphaModifier(1.0f, 0.0f, 1.0f),
            						new MoveModifier(3.0f, vehicle3[i3].getX(), 550.0f, 
           							vehicle3[i3].getY(), startY)));
             	scene.getLastChild().attachChild(vehicle3[i3]);
             	
            	if (nDanger3 < 500){
             		Random ranTime = new Random();
            		
            			int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	
    						
             		mHandler.postDelayed(mStartVehicle3,1000*Randomnumber);
             	}
             }};
             
             
             private Runnable mStartVehicle4 = new Runnable() {
                 public void run() {
                 	int i4 = nDanger4++;
                 	Scene scene = Level1Activity.this.mEngine.getScene();
                    	float startY = 207;
                    	vehicle4[i4] = new AnimatedSprite(ROAD_START + 30.0f, startY, mCarBlueTextureRegion.clone());
                 	final long[] frameDurations = new long[26];
                 	Arrays.fill(frameDurations, 1);
                     vehicle4[i4].animate(frameDurations, 0, 25, true);
                    	vehicle4[i4].registerEntityModifier(
                    			new SequenceEntityModifier (
                    						new AlphaModifier(1.0f, 0.0f, 1.0f),
                   						new MoveModifier(3.0f, vehicle4[i4].getX(), 650.0f, 
                  							vehicle4[i4].getY(), startY)));
                    	scene.getLastChild().attachChild(vehicle4[i4]);
                    	
                   	if (nDanger4 < 500){
                    		Random ranTime = new Random();
                   		
                   			int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	
           						
                    		mHandler.postDelayed(mStartVehicle4,1000*Randomnumber);
                    	}
                    }};
                    
                    private Runnable mStartFood = new Runnable() {
                        public void run() {
                        	int i5 = nfood++;
                        	Scene scene = Level1Activity.this.mEngine.getScene();
                           	//float startY = 211;
                           	Random randomWormX = new Random();
                      		int RandomnumberWormX = randomWormX.nextInt(400);
                      		
                           	Random randomWormY = new Random();
                      		int RandomnumberWormY = randomWormY.nextInt(230);
                      		if (RandomnumberWormY<100)RandomnumberWormX=+100;

                           	worm[i5] = new AnimatedSprite(RandomnumberWormX, RandomnumberWormY, mWormTextureRegion.clone());
                        	if (i5>0){scene.getLastChild().detachChild(worm[i5-1]);}
                           	scene.getLastChild().attachChild(worm[i5]);
                           	
                          	if (nfood< 500){
                           		Random ranTime = new Random();
                          		
                          			int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	
                  						
                           		mHandler.postDelayed(mStartFood,1500*Randomnumber);
                           	}
                           }};
                           
                           private Runnable mStartChicken = new Runnable() {
                               public void run() {
                               	int c1 = nChicken++;
                               	Scene scene = Level1Activity.this.mEngine.getScene();
                                  	//float startY = 211;
                               		int chickenX=220;
                               		int chickenY= 240;
                               	
                               		

                               		myChicken[c1] = new AnimatedSprite(chickenX, chickenY, mMyChickenTextureRegion.clone());

                                  	
                                 	if (nChicken== 1){
	// random from 1 to 49	
                                      	scene.getLastChild().attachChild(myChicken[c1]);
                                  		mHandler.postDelayed(mStartChicken,1000);
                                  	}
                                  }};
                                  
                                  private Runnable mStartControl = new Runnable() {
                                      public void run() {
                                      	 nControl++;
                                      	Scene scene = Level1Activity.this.mEngine.getScene();

                                      		

                                      		Control = new AnimatedSprite(0, 255, mControlTextureRegion.clone());

                                         	
                                        	if (nControl== 1){
       	// random from 1 to 49	
                                             	scene.getLastChild().attachChild(Control);
                                         		mHandler.postDelayed(mStartControl,1000);
                                         	}
                                         }};
                                         private Runnable mStartKnob = new Runnable() {
                                             public void run() {
                                             	 nKnob++;
                                             	Scene scene = Level1Activity.this.mEngine.getScene();

                                             		

                                             		Knob = new AnimatedSprite(32, 248, mKnobTextureRegion.clone());

                                                	
                                               	if (nKnob== 1){
              	// random from 1 to 49	
                                                    	scene.getLastChild().attachChild(Knob);
                                                		mHandler.postDelayed(mStartKnob,1000);
                                                	}
                                                }};
}
