package com.mhieu.spaceship.entity.item;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mhieu.spaceship.Constant;

public class HeartItem extends Sprite {

	public HeartItem(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		
		sHeartItemList.add(this);
	}
	
	private static ArrayList<HeartItem> sHeartItemList = new ArrayList<HeartItem>();
	public static ArrayList<HeartItem> getHeartItemList()
	{
		return sHeartItemList;
	}
	
	private boolean mIsDead = false;
	private float mDropSpeed = 100;
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(isDead())
		{
			return;
		}
		
		setY(getY() + mDropSpeed * pSecondsElapsed);
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
	
	public static void cleanHeartItemList()
	{
		Iterator<HeartItem> itr = sHeartItemList.iterator();
		while(itr.hasNext())
		{
			HeartItem heart = itr.next();
			if(heart.isDead())
			{
				itr.remove();
				heart.detachSelf();
				heart.dispose();
			}			
		}
	}
}
