package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;
import walnoot.ld31.Time;

public class HealthEntity extends SpriteEntity {
	private static final int INVULNERABILITY_TIME = (int) (0.25f * Time.FPS);
	
	private int health, maxHealth;
	
	private int invulnerabilityTicks;
	
	public HealthEntity(LD31Game game, String spriteName, int maxHealth) {
		super(game, spriteName);
		
		this.maxHealth = maxHealth;
		health = maxHealth;
	}
	
	@Override
	public void update() {
		if (isInvulnerable()) invulnerabilityTicks--;
	}
	
	public void damage(int amount) {
		if (!isInvulnerable()) {
			health -= amount;
			onDamage();
			if (health <= 0) {
				health = 0;
				remove();
			}
			
			invulnerabilityTicks = INVULNERABILITY_TIME;
		}
	}
	
	protected void onDamage() {
	}
	
	public void heal(int amount) {
		health += amount;
		if (health > maxHealth) health = maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public boolean isInvulnerable() {
		return invulnerabilityTicks > 0;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}
