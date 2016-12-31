package com.mhieu.spaceship.entity.player;

import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlayerBullet extends Sprite {

	public PlayerBullet(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		sPlayerBulletList.add(this);
	}
	
	private static ArrayList<PlayerBullet> sPlayerBulletList = new ArrayList<PlayerBullet>();
	public static ArrayList<PlayerBullet> getPlayerBulletList()
	{
		return sPlayerBulletList;
	}

	private boolean mIsDead = false;
	private float mSpeed = -300;
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		setY(getY() + mSpeed * pSecondsElapsed);
		if((getY() - getHeight()) < 0)
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
	
	public static void cleanPlayerBulletList()
	{
		Iterator<PlayerBullet> itr = sPlayerBulletList.iterator();
		while(itr.hasNext())
		{
			PlayerBullet bullet = itr.next();
			if(bullet.isDead())
			{
				itr.remove();
				bullet.detachSelf();
				bullet.dispose();
			}			
		}
	}
}
