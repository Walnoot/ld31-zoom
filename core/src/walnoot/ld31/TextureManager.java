package walnoot.ld31;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
	private HashMap<String, TextureRegion> regions = new HashMap<String, TextureRegion>();
	
	public TextureManager() {
		regions.put("dot", new TextureRegion(getTexture("dot.png")));
		regions.put("box", new TextureRegion(getTexture("box.png")));
		regions.put("sword", new TextureRegion(getTexture("sword.png")));
		regions.put("coin", new TextureRegion(getTexture("coin.png")));
		regions.put("heart", new TextureRegion(getTexture("heart.png")));
		regions.put("heart_grey", new TextureRegion(getTexture("heart_grey.png")));
		regions.put("gameover", new TextureRegion(getTexture("gameover.png")));
		regions.put("vignette", new TextureRegion(getTexture("vignette.png")));
		regions.put("ladder", new TextureRegion(getTexture("ladder.png")));
		regions.put("hero", new TextureRegion(getTexture("hero.png")));
		regions.put("baddie", new TextureRegion(getTexture("baddie.png")));
		regions.put("switch", new TextureRegion(getTexture("switch.png")));
		regions.put("machine", new TextureRegion(getTexture("machine.png")));
		regions.put("intro", new TextureRegion(getTexture("intro.png")));
	}
	
	private Texture getTexture(String file) {
		Texture texture = new Texture(Gdx.files.internal(file), true);
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		return texture;
	}
	
	public TextureRegion get(String name) {
		return regions.get(name);
	}
}
