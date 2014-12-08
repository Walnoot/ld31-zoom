package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

public class LadderEntity extends SpriteEntity {
	public LadderEntity(LD31Game game, float x, float y) {
		super(game, "ladder");
		pos.set(x, y);
	}
	
	@Override
	public void update() {
		if (world.getPlayer().pos.dst2(pos) < 0.25f) {
			world.goUpper();
		}
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
}
