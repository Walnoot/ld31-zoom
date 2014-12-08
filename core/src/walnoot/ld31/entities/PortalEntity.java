package walnoot.ld31.entities;

import walnoot.ld31.LD31Game;
import walnoot.ld31.Time;
import walnoot.ld31.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class PortalEntity extends SpriteEntity {
	private static final int FBO_SIZE = 512;
	public static final int ZOOM_TIME = (int) (1f * Time.FPS);
	
	private World nextWorld;
	private FrameBuffer frameBuffer;
	private int zoomTimer;
	
	public PortalEntity(LD31Game game, World nextWorld, float x, float y) {
		super(game);
		this.nextWorld = nextWorld;
		
		pos.set(x, y);
	}
	
	@Override
	public void update() {
		if (world.getPlayer().pos.dst2(pos) < 0.25f) {
			zoomTimer++;
			if (zoomTimer > ZOOM_TIME) zoomTimer = ZOOM_TIME;
		} else if (zoomTimer > 0) {
			zoomTimer--;
		}
	}
	
	private void renderWorld() {
		frameBuffer = new FrameBuffer(Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		frameBuffer.begin();
		
		OrthographicCamera camera = new OrthographicCamera(2f, 2f);
		camera.zoom = World.SIZE + .5f;
		camera.update();
		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		nextWorld.render(batch, false);
		batch.end();
		
		frameBuffer.end();
		
		frameBuffer.getColorBufferTexture().bind();
		Gdx.gl20.glGenerateMipmap(GL20.GL_TEXTURE_2D);
		
		frameBuffer.getColorBufferTexture().setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		
		sprite = new Sprite(frameBuffer.getColorBufferTexture());
		sprite.flip(false, true);
//		sprite = new Sprite(new Texture("dot.png"));
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public int getLevel() {
		return -1;
	}
	
	public int getZoomTimer() {
		return zoomTimer;
	}
	
	public World getNextWorld() {
		return nextWorld;
	}
	
	@Override
	public void show() {
		renderWorld();
	}
	
	@Override
	public void hide() {
		frameBuffer.dispose();
		frameBuffer = null;
	}
}
