package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

public class CoinEntity extends SpriteEntity {
	public CoinEntity(LD31Game game) {
		super(game, "coin");
	}
	
	public CoinEntity(LD31Game game, float x, float y) {
		this(game);
		
		pos.set(x, y);
	}
	
	@Override
	public void update() {
		if (world.getPlayer().pos.dst2(pos) < 1f) {
			world.getPlayer().addCoins(1);
			remove();
		}
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public float getWidth() {
		return 0.5f;
	}
	
	@Override
	public float getHeight() {
		return 0.5f;
	}
}
