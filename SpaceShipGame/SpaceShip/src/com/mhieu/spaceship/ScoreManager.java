package com.mhieu.spaceship;

public class ScoreManager {
	
	private static ScoreManager instance = new ScoreManager();
	private ScoreManager() {
		
	}
	
	public static ScoreManager getInstance()
	{
		return instance;
	}
	
	private long mScore = 0;
	
	public long getCurrentScore()
	{
		return mScore;
	}
	
	public void increaseScore(long pNumber)
	{
		mScore += pNumber;
	}
	
	public void resetScore()
	{
		mScore = 0;
	}
}
