package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;
import walnoot.ld31.World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Comparable<Entity> {
	public final Vector2 pos = new Vector2();
	protected final LD31Game game;
	public World world;
	private boolean removed;
	
	public Entity(LD31Game game) {
		this.game = game;
	}
	
	public void update() {
	}
	
	public void render(SpriteBatch batch) {
	}
	
	@Override
	public int compareTo(Entity o) {
		return Integer.compare(getLevel(), o.getLevel());
	}
	
	public int getLevel() {
		return 0;
	}
	
	public void show() {
	}
	
	public void hide() {
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
}
