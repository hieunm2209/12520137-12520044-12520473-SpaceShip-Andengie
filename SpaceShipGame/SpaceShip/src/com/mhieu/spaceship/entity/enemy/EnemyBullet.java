package com.mhieu.spaceship.entity.enemy;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mhieu.spaceship.Constant;

public class EnemyBullet extends Sprite {

	public EnemyBullet(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		sEnemyBulletList.add(this);
	}

	private static ArrayList<EnemyBullet> sEnemyBulletList = new ArrayList<EnemyBullet>();
	public static ArrayList<EnemyBullet> getEnemyBulletList()
	{
		return sEnemyBulletList;
	}
	
	private boolean mIsDead = false;
	private float mSpeed = 200;
	private long mDamage = 1;
	
	public long getDamage()
	{
		return mDamage;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(isDead())
		{
			return;
		}
		
		setY(getY() + mSpeed * pSecondsElapsed);
		if(getY() > Constant.CAMERA_HEIGHT)
		{
			setDead(true);
		}
	}
	
	public boolean isDead()
	{
		return mIsDead;
	}
	
	public void setDead(boolean pDead)
	{
		mIsDead = pDead;
	}
	
	public static void cleanEnemyBulletList()
	{
		Iterator<EnemyBullet> itr = sEnemyBulletList.iterator();
		while(itr.hasNext())
		{
			EnemyBullet bullet = itr.next();
			if(bullet.isDead())
			{
				itr.remove();
				bullet.detachSelf();
				bullet.dispose();
			}			
		}
	}

}
