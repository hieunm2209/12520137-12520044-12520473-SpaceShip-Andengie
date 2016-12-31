package com.mhieu.spaceship.scene;

import org.andengine.engine.Engine;

import com.mhieu.spaceship.ResourceManager;

public class SceneManager {
	private static SceneManager instance = new SceneManager();
	
	private SceneManager() {
		
	}
	
	public static SceneManager getInstance()
	{
		return instance;
	}
	
	public enum eSceneType
	{
		SCENE_MENU,
		SCENE_GAME,
		SCENE_GAMEOVER
	}

	private AbstractScene mCurrentScene;
	private Engine mEngine = ResourceManager.getInstance().getEngine();
	
	public void setScene(AbstractScene pScene)
	{
		AbstractScene temp = mCurrentScene;
		mCurrentScene = pScene;
		mCurrentScene.createScene();
		mEngine.setScene(pScene);
		temp.destroy();
	}
	
	public void setScene(eSceneType pSceneType)
	{
		switch (pSceneType)
		{
		case SCENE_MENU:
			setScene(new MenuScene());
			break;
		case SCENE_GAME:
			setScene(new GameScene());
			break;
		case SCENE_GAMEOVER:
			setScene(new GameOverScene());
			break;
		default:
			break;
		}
	}
	
	public void createMenuScene()
	{
		mCurrentScene = new MenuScene();
		mCurrentScene.createScene();
		mEngine.setScene(mCurrentScene);
	}
	
	public AbstractScene getCurrentScene()
	{
		return mCurrentScene;
	}

}
