package com.mhieu.spaceship.scene;

import org.andengine.audio.music.Music;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import com.mhieu.spaceship.ScoreManager;
import com.mhieu.spaceship.entity.EntityFactory;
import com.mhieu.spaceship.entity.effect.Explosion;
import com.mhieu.spaceship.entity.enemy.Asteroid;
import com.mhieu.spaceship.entity.enemy.EnemyBullet;
import com.mhieu.spaceship.entity.enemy.EnemyShip;
import com.mhieu.spaceship.entity.item.HeartItem;
import com.mhieu.spaceship.entity.player.PlayerBullet;
import com.mhieu.spaceship.entity.player.PlayerShip;
import com.mhieu.spaceship.scene.SceneManager.eSceneType;

public class GameScene extends AbstractScene {

	private Entity mBackgroundLayer = new Entity();
	private Entity mGameObjectsLayer = new Entity();
	private Sprite mBackground1;
	private Sprite mBackground2;
	private Sprite mBackground3;
	private float mBackgroundSpeed = 15;
	private EntityFactory mEntityFactory = EntityFactory.getInstance();
	private PlayerShip mPlayerShip;
	private long mStartAsteroidTime = System.currentTimeMillis();
	private long mAsteroidDelayTime = 2500;
	private long mStartEnemyTime = System.currentTimeMillis();
	private long mEnemyDelayTime = 4000;
	private long mStartHeartItemTime = System.currentTimeMillis();
	private long mHeartItemDelayTime = 25000;
	private Music mMusic = mRes.getGameSceneMusic();
	private HUD mHud;
	private Text mScoreText;
	private Entity mHealthbar;
	
	public GameScene() 
	{
		super();
		mSceneType = eSceneType.SCENE_GAME;
	}
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createHUD();
		mMusic.play();
		mMusic.setLooping(true);
		attachChild(mBackgroundLayer);
		attachChild(mGameObjectsLayer);
		
		createBackground();
		mPlayerShip = mEntityFactory.createPlayerShip(mCamera.getWidth() / 2, mCamera.getHeight() - 100);
		mGameObjectsLayer.attachChild(mPlayerShip);
		
		createHealthBar();
		ScoreManager.getInstance().resetScore();
		
		this.setOnSceneTouchListener(new IOnSceneTouchListener() {	
			
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				// TODO Auto-generated method stub
				if(!mPlayerShip.isDead())
				{
					if (pSceneTouchEvent.isActionMove() || pSceneTouchEvent.isActionDown()) {
					mPlayerShip.clearEntityModifiers();
					float distanceX = pSceneTouchEvent.getX() - mPlayerShip.getX();
					float distanceY = pSceneTouchEvent.getY() - mPlayerShip.getY();
					float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY); 
					if(distance > 0)
					{
						mPlayerShip.registerEntityModifier(new MoveModifier(distance / mPlayerShip.getSpeed(), mPlayerShip.getX(), pSceneTouchEvent.getX(),
							mPlayerShip.getY(), pSceneTouchEvent.getY()));
					}
					mPlayerShip.limitCamera(mCamera);
					return true;
					}				
					else {
						mPlayerShip.clearEntityModifiers();
						mPlayerShip.limitCamera(mCamera);
					}
				}
				return false;
				}});
		
		this.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				if(mPlayerShip.isDead())
				{
					SceneManager.getInstance().setScene(eSceneType.SCENE_GAMEOVER);
					return;
				}
				
				mScoreText.setText("Score: " + ScoreManager.getInstance().getCurrentScore());
				updateBackground();
				createAsteroid();
				createEnemyShip();
				createHeartItem();
				
				mPlayerShip.update(pSecondsElapsed);
				updateHealthBar();
				
				PlayerBullet.cleanPlayerBulletList();
				Asteroid.cleanAsteroidList();
				EnemyShip.cleanEnemyShipList();
				EnemyBullet.cleanEnemyBulletList();
				Explosion.cleanExplosionList();
				HeartItem.cleanHeartItemList();
			}
		});
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if(mMusic != null && mMusic.isPlaying())
		{
			mMusic.pause();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if(mMusic != null && !mMusic.isPlaying())
		{
			mMusic.play();
		}
	}
	
	private void createBackground()
	{
		/*
		mBackground = new RepeatingSpriteBackground(mCamera.getWidth(), mCamera.getHeight(), mActivity.getTextureManager(),
				AssetBitmapTextureAtlasSource.create(mActivity.getAssets(), "gfx/space.png"), mActivity.getVertexBufferObjectManager());
		setBackground(mBackground);
		*/
		mBackground2 = new Sprite(0, 0, mRes.getBackgroundTextureRegion(), mVbom);
		mBackground1 =  new Sprite(0, mBackground2.getY() + mBackground2.getHeight(), mRes.getBackgroundTextureRegion(), mVbom);
		mBackground3 = new Sprite(0, mBackground2.getY() - mBackground2.getHeight(), mRes.getBackgroundTextureRegion(), mVbom);
		mBackgroundLayer.attachChild(mBackground1);
		mBackgroundLayer.attachChild(mBackground2);
		mBackgroundLayer.attachChild(mBackground3);
	}
	
	private void updateBackground()
	{
		mBackground1.setY(mBackground1.getY() + mBackgroundSpeed);
		mBackground2.setY(mBackground2.getY() + mBackgroundSpeed);
		mBackground3.setY(mBackground3.getY() + mBackgroundSpeed);
		
		if(mBackground1.getY() > mCamera.getHeight())
		{
			Sprite temp = mBackground1;
			mBackground1 = mBackground2;
			mBackground2 = mBackground3;
			mBackground3 = temp;
			mBackground3.setY(mBackground2.getY() - mBackground3.getHeight());
		}
	}
	
	private void createHeartItem()
	{
		long currentTime = System.currentTimeMillis();
		if((currentTime - mStartHeartItemTime) >= mHeartItemDelayTime)
		{
			HeartItem heart = mEntityFactory.createHeartItem((float) (Math.random() * mCamera.getWidth()), -50);
			mGameObjectsLayer.attachChild(heart);
			mStartHeartItemTime = currentTime;
		}
	}
	
	private void createAsteroid()
	{
		long currentTime = System.currentTimeMillis();
		if((currentTime - mStartAsteroidTime) >= mAsteroidDelayTime)
		{
			Asteroid ast = mEntityFactory.createAsteroid((float) (Math.random() * mCamera.getWidth()), -50);
			mGameObjectsLayer.attachChild(ast);
			mStartAsteroidTime = currentTime;
		}
	}
	
	private void createEnemyShip()
	{
		long currentTime = System.currentTimeMillis();
		if((currentTime - mStartEnemyTime) >= mEnemyDelayTime)
		{
			int ihealth = 2;
			ihealth += ScoreManager.getInstance().getCurrentScore() / 200; 	
			EnemyShip enemy = mEntityFactory.createEnemyShip((float) (Math.random() * mCamera.getWidth()), -50, ihealth);
			mGameObjectsLayer.attachChild(enemy);
			mStartEnemyTime = currentTime;
		}
	}
	
	private void createHUD()
	{
		mHud = new HUD();
		mScoreText = new Text(20, 20, mRes.getGameFont(), "Score: ", 20, new TextOptions(HorizontalAlign.LEFT), mVbom);
		mHud.attachChild(mScoreText);
		mCamera.setHUD(mHud);
	}
	
	private void createHealthBar()
	{
		mHealthbar = new Entity();
		mHud.attachChild(mHealthbar);
		Sprite healthSprite = new Sprite(mCamera.getWidth() / 2 + 20, 40, mRes.getHeathBarTextureRegion(), mVbom);
		mHealthbar.attachChild(healthSprite);
		for(int i = 1; i < 30; i++)
		{
			Sprite sprite = new Sprite(healthSprite.getX() + healthSprite.getWidth() * i, healthSprite.getY(), 
					mRes.getHeathBarTextureRegion(), mVbom);
			mHealthbar.attachChild(sprite);
		}
	}
	
	private void updateHealthBar()
	{
		int health = (int) mPlayerShip.getHealth();
		
		if(health < 0)
		{
			health = 0;
		}
		
		for (int i = 0; i < health; i++)
		{
			mHealthbar.getChildByIndex(i).setVisible(true);
		}
		
		for(int i = health; i < mHealthbar.getChildCount(); i++)
		{
			mHealthbar.getChildByIndex(i).setVisible(false);
		}
	}
	
	@Override
	public void destroy()
	{
		mMusic.stop();
		mCamera.setHUD(null);
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}
}
