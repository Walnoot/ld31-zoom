package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

import com.badlogic.gdx.graphics.Color;

public class PrePortalEntity extends SpriteEntity {
	private int metadata;
	
	public PrePortalEntity(LD31Game game, float x, float y, int metadata) {
		super(game, "box");
		this.metadata = metadata;
		pos.set(x, y);
	}
	
	@Override
	protected Color getColor() {
		return Color.LIGHT_GRAY;
	}
	
	public int getMetadata() {
		return metadata;
	}
}
