package com.mhieu.spaceship.entity;

import com.mhieu.spaceship.ResourceManager;
import com.mhieu.spaceship.entity.effect.Explosion;
import com.mhieu.spaceship.entity.enemy.Asteroid;
import com.mhieu.spaceship.entity.enemy.EnemyBullet;
import com.mhieu.spaceship.entity.enemy.EnemyShip;
import com.mhieu.spaceship.entity.item.HeartItem;
import com.mhieu.spaceship.entity.player.PlayerBullet;
import com.mhieu.spaceship.entity.player.PlayerShip;

public class EntityFactory {
	private static EntityFactory instance = new EntityFactory();
	
	private EntityFactory() {
		
	}
	
	public static EntityFactory getInstance() {
		return instance;
	}
	
	
	private ResourceManager mRes= ResourceManager.getInstance();
	
	public PlayerShip createPlayerShip(float pX, float pY) {
		return new PlayerShip(pX, pY, mRes.getPlayerShipTextureRegion(), mRes.getVbom()); 
	}
	
	public PlayerBullet createPlayerBullet(float pX, float pY) {
		return new PlayerBullet(pX, pY, mRes.getPlayerBulletTextureRegion(), mRes.getVbom());
	}
	
	public EnemyShip createEnemyShip(float pX, float pY, int pHealth) {
		return new EnemyShip(pX, pY, pHealth, mRes.getEnemyShipTextureRegion(), mRes.getVbom());
	}
	
	public EnemyBullet createEnemyBullet(float pX, float pY) {
		return new EnemyBullet(pX, pY, mRes.getEnemyBulletTextureRegion(), mRes.getVbom());
	}
	
	public Asteroid createAsteroid(float pX, float pY) {
		return new Asteroid(pX, pY, mRes.getAsteroidTextureRegion(), mRes.getVbom());
	}
	
	public HeartItem createHeartItem(float pX, float pY) {
		return new HeartItem(pX, pY, mRes.getHeartTextureRegion(), mRes.getVbom());
	}
	
	public Explosion createExplosion(float pX, float pY) {
		return new Explosion(pX, pY, mRes.getExplosionTextureRegion(), mRes.getVbom());
	}
}
