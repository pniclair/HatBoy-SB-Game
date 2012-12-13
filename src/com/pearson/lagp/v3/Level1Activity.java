package com.pearson.lagp.v3;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 00:06:23 - 11.07.2010
 */
public class Level1Activity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static final int ROAD_START = -120;
	// ===========================================================
	// Fields
	// ============================================

	private Camera mCamera;

	private Texture mChickenTexture;
	private TextureRegion mChickenTextureRegion;

	private Texture mTestTexture;

	private Texture mBackgroundTexture;
	private Texture mTruckBlueTexture;
	private Texture mTruckRedTexture;
	private Texture mCarRedTexture;
	private Texture mCarBlueTexture;
	private Texture mWormTexture;
	private Texture mgameOverTexture;
	
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private TextureRegion mBackgroundTextureRegion;
	private TiledTextureRegion mTruckBlueTextureRegion;
	private TiledTextureRegion mTruckRedTextureRegion;
	private TiledTextureRegion mCarRedTextureRegion;
	private TiledTextureRegion mCarBlueTextureRegion;
	private TiledTextureRegion mWormTextureRegion;
	private TextureRegion mTestTextureRegion;
	private TextureRegion mgameOverTextureRegion;


	private AnimatedSprite[] vehicle1 = new AnimatedSprite[500];
	private AnimatedSprite[] vehicle2 = new AnimatedSprite[500];
	private AnimatedSprite[] vehicle3= new AnimatedSprite[500];
	private AnimatedSprite[] vehicle4 = new AnimatedSprite[500];
	private AnimatedSprite[] worm = new AnimatedSprite[500];

	private Handler mHandler;
	private int nDanger1;
	private int nDanger2;
	private int nDanger3;
	private int nDanger4;
	private int nfood;
	private int i1;
	private int i2;
	private int i3;
	private int i4;
	private int i5;
	private Music backgroundMusic;

	private Sprite ChickenPlaying;

	private Texture mFontTexture;
	private Font mFont;

	private ChangeableText score;
	private ChangeableText life;
	private int lives=5;
	private int scoreCount=0;


	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/InterChickens
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		mHandler = new Handler();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		TextureRegionFactory.setAssetBasePath("gfx/Level1/");

		mBackgroundTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "level1bk.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mBackgroundTexture);

		mFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mEngine.getTextureManager().loadTexture(mFontTexture);


		mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT,
				Typeface.BOLD), 14, true, Color.RED);


		mEngine.getFontManager().loadFont(mFont);


		mChickenTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mChickenTextureRegion = TextureRegionFactory.createFromAsset(this.mChickenTexture, this, "myChicken.png", 0, 0);

		mOnScreenControlTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);

		mEngine.getTextureManager().loadTextures(this.mChickenTexture, this.mOnScreenControlTexture);

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

		mTestTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTestTextureRegion = TextureRegionFactory.createFromAsset(this.mTestTexture, this, "myChicken.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mTestTexture);

		mgameOverTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mgameOverTextureRegion = TextureRegionFactory.createFromAsset(this.mgameOverTexture, this, "gameOver.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mgameOverTexture);

		MusicFactory.setAssetBasePath("mfx/");
		
		try {
			backgroundMusic = MusicFactory.createMusicFromAsset(mEngine
					.getMusicManager(), this, "panther.wav");
			backgroundMusic.setLooping(true);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);

		final int centerX = (CAMERA_WIDTH - mBackgroundTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - mBackgroundTextureRegion.getHeight()) / 2;
		final Sprite background = new Sprite(centerX, -centerY, mBackgroundTextureRegion);
		final Sprite Chicken = new Sprite(220, 270,mChickenTextureRegion);
		final Sprite gameOver = new Sprite(135, 100,mgameOverTextureRegion);


		//final Sprite Test = new Sprite(290, 240 ,mTestTextureRegion);

		backgroundMusic.play();

		final PhysicsHandler physicsHandler = new PhysicsHandler(Chicken);
		Chicken.registerUpdateHandler(physicsHandler);
		scene.getLastChild().attachChild(background);
		scene.getLastChild().attachChild(Chicken);
		//	scene.getLastChild().attachChild(Test);

		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, 200, new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				Chicken.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 0.75f, 0.75f), new ScaleModifier(0.25f,  0.75f, 0.75f)));


			}
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(analogOnScreenControl);
		nDanger1 = 0;
		nDanger2 = 0;
		nDanger3 = 0;
		nDanger4 = 0;
		nfood=0;
		//scene.getLastChild().attachChild(background);
		mHandler.postDelayed(mStartVehicle1,1000);
		mHandler.postDelayed(mStartVehicle2,1000);
		mHandler.postDelayed(mStartVehicle3,1000);
		mHandler.postDelayed(mStartVehicle4,1000);
		mHandler.postDelayed(mStartFood,1000);
		score = new ChangeableText(435, 276, mFont, String.valueOf(scoreCount));
		scene.getLastChild().attachChild(score);
		life = new ChangeableText(435, 291, mFont, String.valueOf(lives));
		scene.getLastChild().attachChild(life);


		//	if (Chicken.getY() >= 500);{
		//		Intent myIntent = new Intent(Level1Activity.this, MainMenuActivity.class);
		//		Level1Activity.this.startActivity(myIntent);
		//		Level1Activity.this.finish();	
		//	
		//	}
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {


				if(vehicle1[i1].collidesWith(Chicken) || vehicle2[i2].collidesWith(Chicken)||vehicle3[i3].collidesWith(Chicken) || vehicle4[i4].collidesWith(Chicken))
				{lives--;
				Chicken.setPosition(220, 270);

				life.setText(String.valueOf(lives));
				}
				if (i1>1){
					if(vehicle1[i1-1].collidesWith(Chicken) || vehicle2[i2-1].collidesWith(Chicken)||vehicle3[i3-1].collidesWith(Chicken) || vehicle4[i4-1].collidesWith(Chicken))
					{lives--;
					Chicken.setPosition(220, 270);
					life.setText(String.valueOf(lives));
					}}

				if(worm[i5].collidesWith(Chicken) )
				{scoreCount++;
				
				score.setText(String.valueOf(scoreCount));
				scene.getLastChild().detachChild(worm[i5]);
				}
				
				if (lives==0){life.setText(String.valueOf("0"));
				scene.getLastChild().attachChild(gameOver);
					mHandler.postDelayed(mLaunchOptionsTask, 500);
					backgroundMusic.stop();	
				}
				


			}
			
		});


		// repositioning the score later so we can use the score.getWidth()

		return scene;
	}

	private Runnable mLaunchOptionsTask = new Runnable() {
		public void run() {
			Intent myIntent = new Intent(Level1Activity.this, MainMenuActivity.class);
			Level1Activity.this.startActivity(myIntent);
		}
	};

	@Override
	public void onLoadComplete() {
	}
	private Runnable mStartFood = new Runnable() {
		public void run() {
			 i5 = nfood++;
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
		private Runnable mStartVehicle1 = new Runnable() {
			public void run() {
				i1 = nDanger1++;
				Scene scene = Level1Activity.this.mEngine.getScene();
				float startY = 64f;
				vehicle1[i1] = new AnimatedSprite(CAMERA_WIDTH - 15.0f, startY, mTruckBlueTextureRegion.clone());
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
				i2 = nDanger2++;
				Scene scene = Level1Activity.this.mEngine.getScene();
				float startY = 111f;
				vehicle2[i2] = new AnimatedSprite(CAMERA_WIDTH - 15.0f, startY, mTruckRedTextureRegion.clone());
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
				i3 = nDanger3++;
				Scene scene = Level1Activity.this.mEngine.getScene();
				float startY = 155;
				vehicle3[i3] = new AnimatedSprite(ROAD_START + 15.0f, startY, mCarRedTextureRegion.clone());
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
					i4 = nDanger4++;
					Scene scene = Level1Activity.this.mEngine.getScene();
					float startY = 207;
					vehicle4[i4] = new AnimatedSprite(ROAD_START + 15.0f, startY, mCarBlueTextureRegion.clone());
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




				// ===========================================================
				// Methods
				// ===========================================================

				// ===========================================================
				// Inner and Anonymous Classes
				// ===========================================================
}
