package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SpriteEntity extends CollidableEntity {
	protected Sprite sprite;
	private float alpha = 1f, scale = 1f;
	
	public SpriteEntity(LD31Game game) {
		super(game);
	}
	
	public SpriteEntity(LD31Game game, String spriteName) {
		super(game);
		setSprite(spriteName);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sprite.setSize(getWidth() * scale, getHeight() * scale);
		sprite.setOrigin(getWidth() * scale / 2f, getHeight() * scale / 2f);
		sprite.setPosition(pos.x - sprite.getOriginX(), pos.y - sprite.getOriginY());
		sprite.setColor(getColor());
		sprite.draw(batch, alpha);
	}
	
	public void setSprite(String name) {
		sprite = new Sprite(game.getTextureManager().get(name));
	}
	
	protected Color getColor() {
		return Color.WHITE;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
}
