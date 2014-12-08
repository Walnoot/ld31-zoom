package walnoot.ld31;

import walnoot.libgdxutils.State;
import walnoot.libgdxutils.Transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class ImageState extends State {
	private OrthographicCamera camera = new OrthographicCamera();
	private SpriteBatch batch = new SpriteBatch();
	private TextureRegion textureRegion;
	private LD31Game game;
	
	public ImageState(LD31Game game, boolean gameover) {
		this.game = game;
		
		if (gameover) textureRegion = game.getTextureManager().get("gameover");
		else textureRegion = game.getTextureManager().get("intro");
	}
	
	@Override
	public void update() {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) manager.transitionTo(new GameState(game),
				new Transition.FadeTransition(1f));
	}
	
	@Override
	public void render(FrameBuffer target) {
		clearScreen(Color.BLACK);
		batch.begin();
		batch.draw(textureRegion, -1f, -1f, 2f, 2f);
		batch.end();
	}
	
	@Override
	public void resize(boolean creation, int width, int height) {
		camera.viewportWidth = 2f * width / height;
		camera.viewportHeight = 2f;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}
}
