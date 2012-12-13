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
	private String tag = "Level1Activity";

	// ===========================================================
	// Fields
	// ===========================================================

	private Handler mHandler;
	
	protected Camera mCamera;

	protected Scene mMainScene;

	private Texture mLevel1BackTexture;
	private Texture mTruckBlueTexture;
	private Texture mTruckRedTexture;
	private BuildableTexture mObstacleBoxTexture;
	
	private TextureRegion mBoxTextureRegion;
	private TextureRegion mLevel1BackTextureRegion;
	private TextureRegion mBulletTextureRegion;
	private TextureRegion mCrossTextureRegion;
	private TextureRegion mHatchetTextureRegion;
	private TiledTextureRegion mTruckBlueTextureRegion;
	private TiledTextureRegion mTruckRedTextureRegion;
	
	private AnimatedSprite[] vehicle1 = new AnimatedSprite[1];
	private AnimatedSprite[] vehicle2 = new AnimatedSprite[1];
	private AnimatedSprite[] vehicle3= new AnimatedSprite[1];
	private AnimatedSprite[] vehicle4 = new AnimatedSprite[1];
	private int nVamp1;
	private int nVamp2;
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
		mLevel1BackTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mLevel1BackTextureRegion = TextureRegionFactory.createFromAsset(this.mLevel1BackTexture, this, "level1bk.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mLevel1BackTexture);
		
	
		
		mTruckBlueTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTruckBlueTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTruckBlueTexture, this, "truckBlueLeft.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mTruckBlueTexture);
		
		
		mTruckRedTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTruckRedTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTruckRedTexture, this, "truckRedLeft.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mTruckRedTexture);
	}
	
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);

		/* Center the camera. */
		final int centerX = (CAMERA_WIDTH - mLevel1BackTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - mLevel1BackTextureRegion.getHeight()) / 2;

		/* Create the sprites and add them to the scene. */
		final Sprite background = new Sprite(centerX, centerY, mLevel1BackTextureRegion);
		scene.getLastChild().attachChild(background);
	

		
       	// Add first vampire (which will add the others)
       	nVamp1 = 0;
    	nVamp2 = 0;
		mHandler.postDelayed(mStartVehicle1,1000);
		mHandler.postDelayed(mStartVehicle2,4000);
//		mHandler.postDelayed(mStartVehicle3,1000);
//		mHandler.postDelayed(mStartVehicle4,1000);
		return scene;
	}

	@Override
	public void onLoadComplete() {
	}
	
    private Runnable mStartVehicle1 = new Runnable() {
        public void run() {
        	int i1 = nVamp1++;
        	Scene scene = Level1Activity.this.mEngine.getScene();
           	float startY = 64f;
           	vehicle1[i1] = new AnimatedSprite(CAMERA_WIDTH - 30.0f, startY, mTruckBlueTextureRegion.clone());
        	final long[] frameDurations = new long[26];
        	Arrays.fill(frameDurations, 1);
            vehicle1[i1].animate(frameDurations, 0, 25, true);
           	vehicle1[i1].registerEntityModifier(
           			new SequenceEntityModifier (
           						new AlphaModifier(1.0f, 0.0f, 1.0f),
          						new MoveModifier(5.0f, vehicle1[i1].getX(), 30.0f, 
         							vehicle1[i1].getY(), startY)));
           	scene.getLastChild().attachChild(vehicle1[i1]);
        	if (nVamp1 < 1){
        		mHandler.postDelayed(mStartVehicle1,1000);
        	}
        }
     };
     
     private Runnable mStartVehicle2 = new Runnable() {
         public void run() {
         	int i2 = nVamp2++;
         	Scene scene = Level1Activity.this.mEngine.getScene();
            	float startY = 111f;
            	vehicle2[i2] = new AnimatedSprite(CAMERA_WIDTH - 30.0f, startY, mTruckRedTextureRegion.clone());
         	final long[] frameDurations = new long[26];
         	Arrays.fill(frameDurations, 1);
             vehicle2[i2].animate(frameDurations, 0, 25, true);
            	vehicle2[i2].registerEntityModifier(
            			new SequenceEntityModifier (
            						new AlphaModifier(1.0f, 0.0f, 1.0f),
           						new MoveModifier(5.0f, vehicle2[i2].getX(), 30.0f, 
          							vehicle2[i2].getY(), startY)));
            	scene.getLastChild().attachChild(vehicle2[i2]);
         	if (nVamp2 < 1){
         		mHandler.postDelayed(mStartVehicle2,1000);
         	}
         }
      };
}
