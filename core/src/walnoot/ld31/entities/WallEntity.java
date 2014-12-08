package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

public class WallEntity extends SpriteEntity {
	
	public WallEntity(LD31Game game, float x, float y) {
		super(game, "box");
		
		pos.set(x, y);
	}
}
