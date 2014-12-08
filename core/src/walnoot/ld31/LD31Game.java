package walnoot.ld31;

import walnoot.libgdxutils.State;
import walnoot.libgdxutils.StateApplication;
import walnoot.libgdxutils.input.InputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class LD31Game extends StateApplication {
	private static final boolean DEBUG = false;
	
	private TextureManager textureManager;
	private InputHandler input;
	
	private Pixmap levels;
	
	public LD31Game() {
		super(Time.FPS, DEBUG);
	}
	
	@Override
	protected void init() {
		textureManager = new TextureManager();
		input = InputHandler.read(Gdx.files.internal("input.json"));
		Gdx.input.setInputProcessor(input);
		levels = new Pixmap(Gdx.files.internal("levels.png"));
	}
	
	@Override
	protected void update() {
		super.update();
		input.update();
	}
	
	@Override
	protected State getFirstState() {
//		return new FadeState(new GameState(this), Color.BLACK, 1f);
		return new ImageState(this, false);
	}
	
	public TextureManager getTextureManager() {
		return textureManager;
	}
	
	public InputHandler getInput() {
		return input;
	}
	
	public Pixmap getLevels() {
		return levels;
	}
}
