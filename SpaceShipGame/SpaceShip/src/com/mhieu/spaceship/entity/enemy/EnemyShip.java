package com.mhieu.spaceship.entity.enemy;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mhieu.spaceship.ResourceManager;
import com.mhieu.spaceship.ScoreManager;
import com.mhieu.spaceship.entity.EntityFactory;
import com.mhieu.spaceship.entity.effect.Explosion;
import com.mhieu.spaceship.entity.player.PlayerBullet;

public class EnemyShip extends Sprite {

	public EnemyShip(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManagerManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManagerManager);
		sEnemyShipList.add(this);
		mOriginX = getX();
	
	}
	
	public EnemyShip(float pX, float pY, int pHealth, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManagerManager) {
		this(pX, pY, pTextureRegion, pVertexBufferObjectManagerManager);
		mHealth = pHealth;
	}
	
	private static ArrayList<EnemyShip> sEnemyShipList = new ArrayList<EnemyShip>();
	public static ArrayList<EnemyShip> getEnemyShipList()
	{
		return sEnemyShipList;
	}
	
	private long mStartShootTime = System.currentTimeMillis();
	private long mDelayShootTime = 1600;
	private float mOriginX;
	private float mDropSpeed = 70;
	private float mRangeX = 200;
	private float mSpeedX = 100;
	private int mDirectionX = (Math.random()>0.5) ? 1 : 0;
	private long mDamage = 2;
	private int mHealth = 1;
	
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
		
		if(mHealth <= 0)
		{
			setDead(true);
			Explosion.explodeEntity(this);
			ScoreManager.getInstance().increaseScore(20);
			return;
		}
		
		ArrayList<PlayerBullet> playerbulletlist = PlayerBullet.getPlayerBulletList();
		for (int i = 0; i < playerbulletlist.size(); i++)
		{
			if(!playerbulletlist.get(i).isDead())
			{
				if(collidesWith(playerbulletlist.get(i)))
				{
					//setDead(true);
					mHealth--;
					ResourceManager.getInstance().getPlayerShootSound().play();
					playerbulletlist.get(i).setDead(true);
					return;
				}
			}
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
		
		long currentTime = System.currentTimeMillis();
		if((currentTime - mStartShootTime) > mDelayShootTime)
		{
			EnemyBullet bullet = EntityFactory.getInstance().createEnemyBullet(0, 0);
			bullet.setX(getX() + getWidth() / 2 - bullet.getWidth() / 2);
			bullet.setY(getY() + getHeight());
			getParent().attachChild(bullet);
			mStartShootTime = currentTime;
		}
	}
	
	private boolean mIsDead = false;
	
	public boolean isDead()
	{
		return mIsDead;
	}
	
	public void setDead(boolean pDead)
	{
		mIsDead = pDead;
	}
	
	public static void cleanEnemyShipList()
	{
		Iterator<EnemyShip> itr = sEnemyShipList.iterator();
		while(itr.hasNext())
		{
			EnemyShip enemy = itr.next();
			if(enemy.isDead())
			{
				itr.remove();
				enemy.detachSelf();
				enemy.dispose();
			}			
		}
	}
}
