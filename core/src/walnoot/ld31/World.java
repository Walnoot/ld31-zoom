package walnoot.ld31;

import walnoot.ld31.entities.CollidableEntity;
import walnoot.ld31.entities.Entity;
import walnoot.ld31.entities.PlayerEntity;
import walnoot.ld31.entities.PortalEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class World {
	public static final int SIZE = 6;
	
	private Array<Entity> entities = new Array<Entity>();
	private Array<Entity> removedEntities = new Array<Entity>();
	private Array<CollidableEntity> collidables = new Array<CollidableEntity>();
	private PlayerEntity player;
	
	private World upperWorld;
	
	private boolean filled;
	
	private boolean goUp;
	
	public World(LD31Game game) {
	}
	
	public void update() {
		removedEntities.clear();
		for (int i = 0; i < entities.size; i++) {
			Entity e = entities.get(i);
			if (e.isRemoved()) removedEntities.add(e);
			else e.update();
		}
		
		entities.removeAll(removedEntities, true);
		for (Entity e : removedEntities) {
			CollidableEntity c = (CollidableEntity) e;
			if (collidables.contains(c, true)) collidables.removeValue(c, true);
		}
		
		doPhysicsX();
		doPhysicsY();
		
		for (CollidableEntity e : collidables) {
			e.vel.set(0f, 0f);
		}
	}
	
	private void doPhysicsX() {
		for (int i = 0; i < collidables.size; i++) {
			CollidableEntity c1 = collidables.get(i);
			
			c1.pos.x += c1.vel.x * Time.DELTA;
			
			for (int j = 0; j < collidables.size; j++) {
				CollidableEntity c2 = collidables.get(j);
				
				if (c1 == c2 || !c1.isMovable()) continue;
				
				if (c1.hits(c2)) {
					if (c1.vel.x > 0f) {//move right
						c1.pos.x = c2.getX1() - c1.getWidth() / 2f - 0.001f;
					}
					
					if (c1.vel.x < 0f) {//move left
						c1.pos.x = c2.getX2() + c1.getWidth() / 2f + 0.001f;
					}
					
					c1.onHit(c2);
					c2.onHit(c1);
				}
			}
		}
	}
	
	private void doPhysicsY() {
		for (int i = 0; i < collidables.size; i++) {
			CollidableEntity c1 = collidables.get(i);
			
			c1.pos.y += c1.vel.y * Time.DELTA;
			
			for (int j = 0; j < collidables.size; j++) {
				CollidableEntity c2 = collidables.get(j);
				
				if (c1 == c2 || !c1.isMovable()) continue;
				
				if (c1.hits(c2)) {
					if (c1.vel.y > 0f) {//move up
						c1.pos.y = c2.getY1() - c1.getHeight() / 2f - 0.001f;
					}
					
					if (c1.vel.y < 0f) {//move down
						c1.pos.y = c2.getY2() + c1.getHeight() / 2f + 0.001f;
					}
					
					c1.onHit(c2);
					c2.onHit(c1);
				}
			}
		}
	}
	
	public void render(SpriteBatch batch, boolean drawPlayer) {
		entities.sort();
		
		for (int i = 0; i < entities.size; i++) {
			Entity e = entities.get(i);
			if ((drawPlayer || !(e instanceof PlayerEntity)) && !e.isRemoved()) e.render(batch);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		e.world = this;
		if (e instanceof CollidableEntity) {
			CollidableEntity collidable = (CollidableEntity) e;
			if (collidable.isSolid()) collidables.add(collidable);
		}
		
		if (e instanceof PlayerEntity) player = (PlayerEntity) e;
	}
	
	public PlayerEntity getPlayer() {
		return player;
	}
	
	public PortalEntity getTargetPortal() {
		PortalEntity target = null;
		
		for (Entity e : entities) {
			if (e instanceof PortalEntity) {
				PortalEntity portal = (PortalEntity) e;
				
				if (target == null || portal.getZoomTimer() > target.getZoomTimer()) target = portal;
			}
		}
		
		return target;
	}
	
	public void scanRegion(float x1, float y1, float x2, float y2, ScanCallback callback) {
		for (int i = 0; i < collidables.size; i++) {
			CollidableEntity e = collidables.get(i);
			if (e.hits(x1, y1, x2, y2)) callback.reportEntity(e);
		}
	}
	
	public void setUpperWorld(World upperWorld) {
		this.upperWorld = upperWorld;
	}
	
	public World getUpperWorld() {
		return upperWorld;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public void show() {
		for (Entity e : entities) {
			e.show();
		}
	}
	
	public void hide() {
		for (Entity e : entities) {
			e.hide();
		}
	}
	
	public Array<Entity> getEntities() {
		return entities;
	}
	
	public void goUpper() {
		goUp = true;
	}
	
	public boolean shouldGoUp() {
		return goUp;
	}
	
	public void setGoUp(boolean goUp) {
		this.goUp = goUp;
	}
}
