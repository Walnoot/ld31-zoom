package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

import com.badlogic.gdx.math.Vector2;

public abstract class CollidableEntity extends Entity {
	public Vector2 vel = new Vector2();
	
	public CollidableEntity(LD31Game game) {
		super(game);
	}
	
	public float getWidth() {
		return 1f;
	}
	
	public float getHeight() {
		return 1f;
	}
	
	public boolean hits(CollidableEntity c2) {
		return getX1() < c2.getX2() && getX2() > c2.getX1() && getY1() < c2.getY2() && getY2() > c2.getY1();
	}
	
	public boolean hits(float x1, float y1, float x2, float y2) {
		return getX1() < x2 && getX2() > x1 && getY1() < y2 && getY2() > y1;
	}
	
	public final float getX1() {
		return pos.x - getWidth() / 2f;
	}
	
	public final float getX2() {
		return pos.x + getWidth() / 2f;
	}
	
	public final float getY1() {
		return pos.y - getHeight() / 2f;
	}
	
	public final float getY2() {
		return pos.y + getHeight() / 2f;
	}
	
	public boolean isMovable() {
		return true;
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public void onHit(CollidableEntity other) {
	}
}
