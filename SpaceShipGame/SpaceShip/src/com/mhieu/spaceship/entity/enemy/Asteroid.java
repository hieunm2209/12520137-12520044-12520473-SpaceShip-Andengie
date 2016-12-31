package com.mhieu.spaceship.entity.enemy;

import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import com.mhieu.spaceship.ScoreManager;
import com.mhieu.spaceship.entity.effect.Explosion;
import com.mhieu.spaceship.entity.player.PlayerBullet;

public class Asteroid extends Sprite {

	public Asteroid(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		sAsteroidList.add(this);
		mOriginX = getX();
		}
	
	private static ArrayList<Asteroid> sAsteroidList = new ArrayList<Asteroid>();
	public static ArrayList<Asteroid> getAsteroidList()
	{
		return sAsteroidList;
	}

	private boolean mIsDead = false;
	private float mOriginX;
	private float mDropSpeed = 80;
	private float mRangeX = 120;
	private float mSpeedX = 80;
	private int mDirectionX = (Math.random()>0.5) ? 1 : 0;
	private long mDamage = 2;
	
	public long getDamage()
	{
		return mDamage;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed)
	{	
		if(isDead())
		{
			return;
		}
		
		ArrayList<PlayerBullet> playerbulletlist = PlayerBullet.getPlayerBulletList();
		for (int i = 0; i < playerbulletlist.size(); i++)
		{
			if(!playerbulletlist.get(i).isDead())
			{
				if(collidesWith(playerbulletlist.get(i)))
				{
					setDead(true);
					ScoreManager.getInstance().increaseScore(10);
					playerbulletlist.get(i).setDead(true);
					Explosion.explodeEntity(this);
					return;
				}
			}
		}
		
		if(getY() > 1280)
		{
			setDead(true);
			return;
		}
		
		setY(getY() + mDropSpeed * pSecondsElapsed);
		
		if(mDirectionX == 0)
		{
			setX(getX() - mSpeedX * pSecondsElapsed);
		}
		else
		{
			setX(getX() + mSpeedX * pSecondsElapsed);
		}
		
		if(getX() <= (mOriginX - mRangeX))
		{
			mDirectionX = 1;
		}
		
		if(getX() >= (mOriginX + mRangeX))
		{
			mDirectionX = 0;
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
	
	public static void cleanAsteroidList()
	{
		Iterator<Asteroid> itr = sAsteroidList.iterator();
		while(itr.hasNext())
		{
			Asteroid ast = itr.next();		
			if(ast.isDead())
			{
				itr.remove();
				ast.detachSelf();
				ast.dispose();
			}			
		}
	}
}
