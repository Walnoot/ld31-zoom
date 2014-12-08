package walnoot.ld31.entities;

import walnoot.ld31.GameState;
import walnoot.ld31.LD31Game;
import walnoot.ld31.ScanCallback;
import walnoot.ld31.Time;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PlayerEntity extends HealthEntity implements ScanCallback {
	private static final float SPEED = 4f;
	private static final int ATTACK_TIME = (int) (0.25f * Time.FPS);
	
	private Vector2 tmp = new Vector2();
	
	private int attackTicks;
	private Sprite sword;
	private Vector2 attackDir = new Vector2(0f, 1f);
	private int coins;
	public GameState state;
	
	public PlayerEntity(LD31Game game) {
		super(game, "hero", 10);
		
		sword = new Sprite(game.getTextureManager().get("sword"));
		sword.setSize(1f, 1f);
		sword.setOrigin(0.5f, 0f);
	}
	
	@Override
	public void update() {
		super.update();
		
		tmp.set(0f, 0f);
		
		if (game.getInput().getKey("left").isTouched()) tmp.x -= 1f;
		if (game.getInput().getKey("right").isTouched()) tmp.x += 1f;
		if (game.getInput().getKey("down").isTouched()) tmp.y -= 1f;
		if (game.getInput().getKey("up").isTouched()) tmp.y += 1f;
		
		tmp.nor();
		if (!tmp.isZero()) attackDir.set(tmp);
		tmp.scl(SPEED);
		vel.add(tmp);
		
		if (isAttacking()) {
			attackTicks--;
			
			world.scanRegion(getX1() + attackDir.x, getY1() + attackDir.y, getX2() + attackDir.x,
					getY2() + attackDir.y, this);
		} else {
			if (game.getInput().getKey("attack").isJustTouched()) attackTicks = ATTACK_TIME;
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		float angle = (attackDir.getAngleRad() - MathUtils.PI / 2f) * MathUtils.radiansToDegrees;
		
		if (isAttacking()) angle += (2f * attackTicks / ATTACK_TIME - 1f) * 90f;
		
		sword.setRotation(angle);
		tmp.set(attackDir).scl(isAttacking() ? 0.25f : -0.25f).add(pos).sub(0.5f, 0f);
		sword.setPosition(tmp.x, tmp.y);
		if (!state.isZooming()) sword.draw(batch);
		
		super.render(batch);
	}
	
	@Override
	public void reportEntity(CollidableEntity e) {
		if (e != this && e instanceof HealthEntity) {
			HealthEntity healthEntity = (HealthEntity) e;
			healthEntity.damage(1);
			
			e.vel.add(tmp.set(attackDir).scl(3f));
		}
		
		if (e instanceof SwitchEntity) {
			((SwitchEntity) e).hit();
		}
		
		if (e instanceof MachineEntity) {
			if (attackTicks == 0 && coins > 0 && getHealth() < getMaxHealth()) {
				heal(1);
				coins--;
			}
		}
	}
	
	@Override
	public void onHit(CollidableEntity other) {
		if (other instanceof EnemyEntity) {
			damage(1);
			
			vel.add(tmp.set(other.vel).scl(10f));
		}
	}
	
	public boolean isAttacking() {
		return attackTicks > 0;
	}
	
	@Override
	public float getWidth() {
		return .8f;
	}
	
	@Override
	public float getHeight() {
		return .8f;
	}
	
	@Override
	protected Color getColor() {
		return isInvulnerable() ? Color.RED : Color.WHITE;
	}
	
	public void addCoins(int amount) {
		coins += amount;
	}
	
	public int getCoins() {
		return coins;
	}
}
