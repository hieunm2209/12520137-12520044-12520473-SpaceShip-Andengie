package com.mhieu.spaceship;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.mhieu.spaceship.scene.AbstractScene;
import com.mhieu.spaceship.scene.SceneManager;

public class MainActivity extends SimpleBaseGameActivity
{
	private Camera mCamera;
	@Override
	public EngineOptions onCreateEngineOptions()
	{
		mCamera = new Camera(0, 0, Constant.CAMERA_WIDTH, Constant.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(Constant.CAMERA_WIDTH, Constant.CAMERA_HEIGHT), mCamera );
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		
		return engineOptions;
	}
	
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, Constant.LIMIT_FPS);
	}
	

	@Override
	protected void onCreateResources() {
		ResourceManager.getInstance().create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());
		ResourceManager.getInstance().loadGameGraphics();
		ResourceManager.getInstance().loadGameAudio();
		ResourceManager.getInstance().loadFont();
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());	
		
		SceneManager.getInstance().createMenuScene();
		
		return SceneManager.getInstance().getCurrentScene();
	}
	
	@Override
	public void onResumeGame() {
		((AbstractScene)getEngine().getScene()).onResume();
		super.onResumeGame();
	}
	
	@Override
	public void onPauseGame() {
		((AbstractScene)getEngine().getScene()).onPause();
		super.onPauseGame();
	}
	
}
