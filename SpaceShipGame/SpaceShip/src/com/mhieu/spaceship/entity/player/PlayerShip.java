package com.mhieu.spaceship.entity.player;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mhieu.spaceship.ResourceManager;
import com.mhieu.spaceship.ScoreManager;
import com.mhieu.spaceship.entity.EntityFactory;
import com.mhieu.spaceship.entity.effect.Explosion;
import com.mhieu.spaceship.entity.enemy.Asteroid;
import com.mhieu.spaceship.entity.enemy.EnemyBullet;
import com.mhieu.spaceship.entity.enemy.EnemyShip;
import com.mhieu.spaceship.entity.item.HeartItem;

public class PlayerShip extends Sprite{

	public PlayerShip(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
	}
	
	private boolean mIsDead = false;
	private long mStartShootTime = System.currentTimeMillis();
	private long mHealth = 20;
	private long mMaxHealth = 20;
	private float mSpeed = 800;
	
	
	public void update(float pSecondsElapsed) 
	{		
		if(isDead())
		{
			return;
		}
		
		ArrayList<HeartItem> heartitemlist = HeartItem.getHeartItemList();
		for (int i = 0; i < heartitemlist.size(); i++)
		{
			if(!heartitemlist.get(i).isDead())
			{
				if(collidesWith(heartitemlist.get(i)))
				{
					increaseHealth(3);
					heartitemlist.get(i).setDead(true);
				}
			}
		}
		
		ArrayList<EnemyBullet> enemybulletlist = EnemyBullet.getEnemyBulletList();
		for (int i = 0; i < enemybulletlist.size(); i++)
		{
			if(!enemybulletlist.get(i).isDead())
			{
				if(collidesWith(enemybulletlist.get(i)))
				{
					decreaseHealth(enemybulletlist.get(i).getDamage());
					ResourceManager.getInstance().getPlayerShootSound().play();
					enemybulletlist.get(i).setDead(true);
					if(mHealth <= 0)
					{
						setDead(true);
						Explosion.explodeEntity(this);
						return;
					}
				}
			}
		}
		
		ArrayList<Asteroid> asteroidlist = Asteroid.getAsteroidList();
		for (int i = 0; i < asteroidlist.size(); i++)
		{
			if(!asteroidlist.get(i).isDead())
			{
				if(collidesWith(asteroidlist.get(i)))
				{
					decreaseHealth(asteroidlist.get(i).getDamage());
					asteroidlist.get(i).setDead(true);
					Explosion.explodeEntity(asteroidlist.get(i));
					ScoreManager.getInstance().increaseScore(20);
					if(mHealth <= 0)
					{
						setDead(true);
						Explosion.explodeEntity(this);
						return;
					}
				}
			}
		}
		
		ArrayList<EnemyShip> enemylist = EnemyShip.getEnemyShipList();
		for (int i = 0; i < enemylist.size(); i++)
		{
			if(!enemylist.get(i).isDead())
			{
				if(collidesWith(enemylist.get(i)))
				{
					decreaseHealth(enemylist.get(i).getDamage());
					enemylist.get(i).setDead(true);
					Explosion.explodeEntity(enemylist.get(i));
					ScoreManager.getInstance().increaseScore(30);
					if(mHealth <= 0)
					{
						setDead(true);
						Explosion.explodeEntity(this);
						return;
					}
				}
			}
		}
		
		long mCurrentTime = System.currentTimeMillis();
		if((mCurrentTime - mStartShootTime) > 500) 
		{
			PlayerBullet bullet = EntityFactory.getInstance().createPlayerBullet(0, 0);
			bullet.setX(getX() + getWidth() / 2 - bullet.getWidth() / 2);
			bullet.setY(getY() - bullet.getHeight());
			getParent().attachChild(bullet);
			//ResourceManager.getInstance().getPlayerShootSound().play();
			mStartShootTime = mCurrentTime;
		}
	}
	
	public void limitCamera(Camera pCamera)
	{
		
		if(getX() < pCamera.getSurfaceX())
		{
			setX(pCamera.getSurfaceX());
		}
		
		if((getX() + getWidth()) > (pCamera.getSurfaceX() + pCamera.getWidth()))
		{
			setX(pCamera.getSurfaceX() + pCamera.getWidth() - getWidth());
		}
		
		if(getY() < pCamera.getSurfaceY())
		{
			setY(pCamera.getSurfaceY());
		}
		
		if((getY() + getHeight()) > (pCamera.getSurfaceX() + pCamera.getHeight()))
		{
			setY(pCamera.getSurfaceX() + pCamera.getHeight() - getHeight());
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
	
	public void decreaseHealth(long pDamage)
	{
		mHealth -= pDamage;
	}
	
	public void increaseHealth(long pHealth)
	{
		mHealth += pHealth;
		if(mHealth > mMaxHealth)
		{
			mHealth = mMaxHealth;
		}
	}
	
	public long getHealth()
	{
		return mHealth;
	}
	
	public float getSpeed()
	{
		return mSpeed;
	}

}
