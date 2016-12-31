package com.mhieu.spaceship.scene;

import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import com.mhieu.spaceship.ScoreManager;
import com.mhieu.spaceship.scene.SceneManager.eSceneType;

public class GameOverScene extends AbstractScene {

	private Text mGameOverText;
	private Text mScoreText;
	private Text mTaptoBackText;
	
	public GameOverScene() {
		// TODO Auto-generated constructor stub
		mSceneType = eSceneType.SCENE_GAMEOVER;
	}
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		mGameOverText = new Text(0, mCamera.getHeight() / 3, mRes.getGameFont(), 
				"GAME OVER", mVbom);
		mGameOverText.setX(mCamera.getWidth() / 2 - mGameOverText.getWidth() / 2);	
		mGameOverText.setScale(1.5f);
		attachChild(mGameOverText);
		
		
		mScoreText = new Text(0, 0, mRes.getGameFont(), 
				"Score: " + ScoreManager.getInstance().getCurrentScore(), mVbom);
		mScoreText.setX(mCamera.getWidth() / 2 - mScoreText.getWidth() / 2);
		mScoreText.setY(mGameOverText.getY() + mGameOverText.getHeight() + 40);
		attachChild(mScoreText);
		
		mTaptoBackText = new Text(0, mCamera.getHeight() / 3 * 2, mRes.getGameFont(), 
				"TAP TO HOME", mVbom);
		mTaptoBackText.setX(mCamera.getWidth() / 2 - mTaptoBackText.getWidth() / 2);
		
		attachChild(mTaptoBackText);
		mTaptoBackText.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new FadeOutModifier(0.4f), new FadeInModifier(0.4f))));
		
		setOnSceneTouchListener(new IOnSceneTouchListener() {
			
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				// TODO Auto-generated method stub
				if(pSceneTouchEvent.isActionDown())
				{
					SceneManager.getInstance().setScene(eSceneType.SCENE_MENU);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

}
