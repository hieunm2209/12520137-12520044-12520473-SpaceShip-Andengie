package com.mhieu.spaceship.scene;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.mhieu.spaceship.scene.SceneManager.eSceneType;

public class MenuScene extends AbstractScene {

	private Entity mBackgroundLayer = new Entity();
	private Sprite mBackground1;
	private Sprite mBackground2;
	private Entity mMenuLayer = new Entity();
	private Text mSpaceShipTitle;
	private Text mTaptoPlayText;
	
	public MenuScene() {
		// TODO Auto-generated constructor stub
		super();
		mSceneType = eSceneType.SCENE_MENU;
	}
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		attachChild(mBackgroundLayer);
		attachChild(mMenuLayer);
		createBackground();
		mSpaceShipTitle = new Text(0, mCamera.getHeight() / 3, mRes.getGameFont(), 
				"SPACESHIP", mVbom);
		mSpaceShipTitle.setScale(2.0f);
		mSpaceShipTitle.setX(mCamera.getWidth() / 2 - mSpaceShipTitle.getWidth() / 2);		
		mSpaceShipTitle.setColor(Color.RED);
		mMenuLayer.attachChild(mSpaceShipTitle);
		
		mTaptoPlayText = new Text(0, mCamera.getHeight() / 3 * 2, mRes.getGameFont(), 
				"TAP TO PLAY", mVbom);
		mTaptoPlayText.setX(mCamera.getWidth() / 2 - mTaptoPlayText.getWidth() / 2);
		
		mMenuLayer.attachChild(mTaptoPlayText);
		mTaptoPlayText.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new FadeOutModifier(0.4f), new FadeInModifier(0.4f))));
		
		setOnSceneTouchListener(new IOnSceneTouchListener() {
			
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				// TODO Auto-generated method stub
				if(pSceneTouchEvent.isActionDown())
				{
					SceneManager.getInstance().setScene(eSceneType.SCENE_GAME);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}
	
	private void createBackground()
	{
		mBackground1 = new Sprite(0, 0, mRes.getBackgroundTextureRegion(), mVbom);
		mBackground2 = new Sprite(0, mBackground1.getHeight(), mRes.getBackgroundTextureRegion(), mVbom);
		mBackgroundLayer.attachChild(mBackground1);
		mBackgroundLayer.attachChild(mBackground2);
	}
	
	@Override
	public void destroy()
	{
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}

}
