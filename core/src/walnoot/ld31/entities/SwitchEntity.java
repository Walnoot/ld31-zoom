package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

public class SwitchEntity extends SpriteEntity {
	private boolean hit;
	
	public SwitchEntity(LD31Game game, float x, float y) {
		super(game, "switch");
		pos.set(x, y);
	}
	
	public void hit() {
		if (!hit) {
			hit = true;
			sprite.flip(true, false);
			if (world.getUpperWorld() != null) {
				for (int i = 0; i < world.getUpperWorld().getEntities().size; i++) {
					Entity e = world.getUpperWorld().getEntities().get(i);
					
					if (e instanceof DoorEntity) ((DoorEntity) e).onSwitchHit();
				}
			}
		}
	}
}
