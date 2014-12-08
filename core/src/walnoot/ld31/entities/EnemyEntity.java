package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;
import walnoot.ld31.Time;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class EnemyEntity extends HealthEntity {
	private static final int STUN_TIME = (int) (0.125f * Time.FPS);
	private static final float SPEED = 3f;
	
	private int stunTicks;
	
	private Vector2 tmp = new Vector2();
	
	public EnemyEntity(LD31Game game) {
		super(game, "baddie", 3);
	}
	
	public EnemyEntity(LD31Game game, float x, float y) {
		this(game);
		pos.set(x, y);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (stunTicks > 0) stunTicks--;
		else vel.add(tmp.set(world.getPlayer().pos).sub(pos).nor().scl(SPEED));
	}
	
	@Override
	protected void onDamage() {
		stunTicks = STUN_TIME;
	}
	
	@Override
	protected Color getColor() {
		return isInvulnerable() ? Color.RED : Color.WHITE;
	}
	
	@Override
	public float getWidth() {
		return 0.8f;
	}
	
	@Override
	public float getHeight() {
		return 0.8f;
	}
}
