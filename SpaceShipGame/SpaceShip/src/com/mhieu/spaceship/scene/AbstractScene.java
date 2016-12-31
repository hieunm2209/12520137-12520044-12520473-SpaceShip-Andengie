package com.mhieu.spaceship.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.mhieu.spaceship.ResourceManager;
import com.mhieu.spaceship.scene.SceneManager.eSceneType;

public abstract class AbstractScene extends Scene {
	protected ResourceManager mRes = ResourceManager.getInstance();
	protected Engine mEngine = mRes.getEngine();
	protected SimpleBaseGameActivity mActivity = mRes.getActivity();
	protected VertexBufferObjectManager mVbom = mRes.getVbom();
	protected Camera mCamera = mRes.getCamera(); 
	
	protected eSceneType mSceneType;
	
	public abstract void createScene();
	
	public abstract void destroy();
	
	public void onBackKeyPressed() {
	Debug.d("Back key pressed");
	} 
	
	public eSceneType getSceneType()
	{
		return mSceneType;
	}
	
	public abstract void onPause();
	public abstract void onResume();
}
