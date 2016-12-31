package com.mhieu.spaceship;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import android.graphics.Typeface;

public class ResourceManager {
	public static ResourceManager instance = null;
	private ResourceManager() {};
	
	public static ResourceManager getInstance() {
		if(instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	// Common Objects
	private SimpleBaseGameActivity mActivity;
	private Engine mEngine;
	private Camera mCamera;
	private VertexBufferObjectManager mVbom;
	
	public SimpleBaseGameActivity getActivity()	{
		return mActivity;
	}
	
	public Engine getEngine() {
		return mEngine;
	}
	
	public Camera getCamera() {
		return mCamera;
	}
	public VertexBufferObjectManager getVbom() {
		return mVbom;
	}
		
	//Game Objects Texture	
	private BuildableBitmapTextureAtlas mGameObjectsTextureAtlas;
	private ITextureRegion mPlayerShipTextureRegion;
	private ITextureRegion mPlayerBulletTextureRegion;
	private ITextureRegion mEnemyShipTextureRegion;
	private ITextureRegion mEnemyBulletTextureRegion;
	private ITextureRegion mAsteroidTextureRegion;
	private ITextureRegion mHeartTextureRegion;
	private TiledTextureRegion mExplosionTextureRegion;
	
	public ITextureRegion getPlayerShipTextureRegion() {
		return mPlayerShipTextureRegion;
	}
	
	public ITextureRegion getPlayerBulletTextureRegion() {
		return mPlayerBulletTextureRegion;
	}
	
	public ITextureRegion getEnemyShipTextureRegion() {
		return mEnemyShipTextureRegion;
	}
	
	public ITextureRegion getEnemyBulletTextureRegion() {
		return mEnemyBulletTextureRegion;
	}
	
	public ITextureRegion getAsteroidTextureRegion() {
		return mAsteroidTextureRegion;
	}
	
	public ITextureRegion getHeartTextureRegion() {
		return mHeartTextureRegion;
	}
	
	public TiledTextureRegion getExplosionTextureRegion() {
		return mExplosionTextureRegion;
	}
	
	// Background Texture
	private BuildableBitmapTextureAtlas mBackgroundTextureAtlas;
	private ITextureRegion mBackgroundTextureRegion;
	
	public ITextureRegion getBackgroundTextureRegion() {
		return mBackgroundTextureRegion;
	}
	
	//Widgets Texture
	private BuildableBitmapTextureAtlas mWidgetsTextureAtlas;
	private ITextureRegion mHeathBarTextureRegion;
	
	public ITextureRegion getHeathBarTextureRegion() {
		return mHeathBarTextureRegion;
	}
	
	// Sound
	private Sound mExplodeSound;
	private Sound mPlayerShootSound;
	
	public Sound getExplodeSound()
	{
		return mExplodeSound;
	}
	
	public Sound getPlayerShootSound()
	{
		return mPlayerShootSound;
	}
	
	//Music
	private Music mGameSceneMusic;
	public Music getGameSceneMusic()
	{
		return mGameSceneMusic;
	}
	
	//Font
	private BitmapTextureAtlas mFontTexture;
	private Font mGameFont;
	public Font getGameFont()
	{
		return mGameFont;
	}
	
	public void create(SimpleBaseGameActivity pActivity, Engine pEngine, Camera pCamera, VertexBufferObjectManager pVbom) {
		mActivity = pActivity;
		mEngine = pEngine;
		mCamera = pCamera;
		mVbom = pVbom;
		}
	
	public void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		mGameObjectsTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 900, 300, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mExplosionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "explosion2.png", 9, 2);
		mPlayerShipTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "playership.png");
		mEnemyShipTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "enemyship.png");
		mAsteroidTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "asteroid.png");
		mHeartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "heart.png");
		mEnemyBulletTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "enemybullet.png");
		mPlayerBulletTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameObjectsTextureAtlas, mActivity.getAssets(), "playerbullet2.png");
		try {
			mGameObjectsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
		}
		catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}		
		mGameObjectsTextureAtlas.load();
		
		mBackgroundTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 740, 920, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBackgroundTextureAtlas, mActivity.getAssets(), "space.png");
		try {
			mBackgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
		}
		catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}		
		mBackgroundTextureAtlas.load();
		
		mWidgetsTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 50, 50, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mHeathBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mWidgetsTextureAtlas,  mActivity.getAssets(), "healthbar.png");		
		try {
			mWidgetsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
		}
		catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}			
		mWidgetsTextureAtlas.load();
	}
	
	public void loadGameAudio() {
		try {
			SoundFactory.setAssetBasePath("sfx/");
			mExplodeSound = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "explode.wav");
			mPlayerShootSound = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "playershoot.wav");			
			MusicFactory.setAssetBasePath("sfx/");
			mGameSceneMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), mActivity, "gamescene.mp3");
		} catch (Exception e) {
		throw new RuntimeException("Error while loading audio", e);
		}
	}
	
	public void loadFont()
	{
		mFontTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		mGameFont = FontFactory.createStroke(mEngine.getFontManager(),
				mFontTexture, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),
				50, true, Color.WHITE_ABGR_PACKED_INT, 3, Color.BLACK_ABGR_PACKED_INT);
		mGameFont.load();
	}
}
