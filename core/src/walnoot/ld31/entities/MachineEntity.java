package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

public class MachineEntity extends SpriteEntity {
	public MachineEntity(LD31Game game, float x, float y) {
		super(game, "machine");
		pos.set(x, y);
	}
}
