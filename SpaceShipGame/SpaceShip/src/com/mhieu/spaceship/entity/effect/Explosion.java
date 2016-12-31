package com.mhieu.spaceship.entity.effect;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mhieu.spaceship.ResourceManager;
import com.mhieu.spaceship.entity.EntityFactory;

public class Explosion extends AnimatedSprite {
	
	public Explosion(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		sExplosionList.add(this);
	}

	private boolean mIsDead = false;
	private static ArrayList<Explosion> sExplosionList = new ArrayList<Explosion>();
	public static ArrayList<Explosion> getExplosionList()
	{
		return sExplosionList;
	}
	
	public boolean isDead()
	{
		return mIsDead;
	}
	
	public void setDead(boolean pDead)
	{
		mIsDead = pDead;
	}
	
	public static void explodeEntity(Entity pEntity)
	{
		Explosion explosion = EntityFactory.getInstance().createExplosion(pEntity.getX(), pEntity.getY());
		pEntity.getParent().attachChild(explosion);
		explosion.animate(50, false, new IAnimationListener() {
			
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
				// TODO Auto-generated method stub
				ResourceManager.getInstance().getExplodeSound().play();
			}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount,
					int pInitialLoopCount) {
				// TODO Auto-generated method stub
				((Explosion) pAnimatedSprite).setDead(true);
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				// TODO Auto-generated method stub
				((Explosion) pAnimatedSprite).setDead(true);
			}
		});
	}
	
	public static void cleanExplosionList()
	{
		Iterator<Explosion> itr = sExplosionList.iterator();
		while(itr.hasNext())
		{
			Explosion explosion = itr.next();		
			if(explosion.isDead() || explosion.getCurrentTileIndex() >= explosion.getTileCount())
			{
				itr.remove();
				explosion.detachSelf();
				explosion.dispose();
			}			
		}
	}

}
