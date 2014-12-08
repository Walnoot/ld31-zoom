package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

import com.badlogic.gdx.graphics.Color;

public class DoorEntity extends SpriteEntity {
	private int metadata;
	
	public DoorEntity(LD31Game game, float x, float y, int metadata) {
		super(game, "box");
		this.metadata = metadata;
		pos.set(x, y);
	}
	
	@Override
	public void update() {
		if (metadata == 255) {
			for (int i = 0; i < world.getEntities().size; i++) {
				if (world.getEntities().get(i) instanceof EnemyEntity) return;
			}
			
			remove();
		}
	}
	
	@Override
	protected Color getColor() {
		return Color.PINK;
	}
	
	public void onSwitchHit() {
		if (metadata == 128) remove();
	}
}
