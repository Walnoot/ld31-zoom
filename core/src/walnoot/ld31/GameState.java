package walnoot.ld31;

import walnoot.ld31.entities.CoinEntity;
import walnoot.ld31.entities.DoorEntity;
import walnoot.ld31.entities.EnemyEntity;
import walnoot.ld31.entities.Entity;
import walnoot.ld31.entities.LadderEntity;
import walnoot.ld31.entities.MachineEntity;
import walnoot.ld31.entities.PlayerEntity;
import walnoot.ld31.entities.PortalEntity;
import walnoot.ld31.entities.PrePortalEntity;
import walnoot.ld31.entities.SwitchEntity;
import walnoot.ld31.entities.WallEntity;
import walnoot.libgdxutils.State;
import walnoot.libgdxutils.Transition;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameState extends State {
	private static final float ICON_SCALE = .075f;
	
	private static final int SHAKE_TIME = (int) (0.25f * Time.FPS);
	
	private OrthographicCamera gameCamera = new OrthographicCamera();
	private OrthographicCamera uiCamera = new OrthographicCamera();
	private SpriteBatch batch = new SpriteBatch();
	
	private World world;
	
	private Vector2 tmp = new Vector2();
	
	private LD31Game game;
	
	private TextureRegion heart, heartGrey, coin, vignette;
	
	private int shakeTicks;
	
	public GameState(LD31Game game) {
		this(game, genWorld(null, game, 0), new PlayerEntity(game));
	}
	
	public GameState(LD31Game game, World world, PlayerEntity player) {
		this.game = game;
		this.world = world;
		
		player.pos.set(0f, 0f);
		player.state = this;
		
		player.world = world;
		if (!world.isFilled()) {
			world.addEntity(player);
			
			world.setFilled(true);
		}
		
		world.setGoUp(false);
		
		for (Entity e : world.getEntities()) {
			if (e instanceof PrePortalEntity) {
				world.addEntity(new PortalEntity(game, genWorld(world, game, ((PrePortalEntity) e).getMetadata()),
						e.pos.x, e.pos.y));
				e.remove();
			}
		}
		
		world.show();
		
		heart = game.getTextureManager().get("heart");
		heartGrey = game.getTextureManager().get("heart_grey");
		coin = game.getTextureManager().get("coin");
		vignette = game.getTextureManager().get("vignette");
	}
	
	private static World genWorld(World upper, LD31Game game, int metadata) {
		World world = new World(game);
		world.setUpperWorld(upper);
		
		int xOffset = 0, yOffset = 0;
		
		if (metadata == 255) {
			yOffset = 1 * 13;
			xOffset = MathUtils.random(0, 3) * 13;
//			xOffset = 3 * 13;
		}
		if (metadata == 128) {
			xOffset = MathUtils.random(0, 3) * 13;
//			xOffset = MathUtils.random(2, 3) * 13;
			yOffset = 2 * 13;
		}
		
		for (int x = -World.SIZE; x <= World.SIZE; x++) {
			for (int y = -World.SIZE; y <= World.SIZE; y++) {
				
				int pixel = game.getLevels().getPixel(x + World.SIZE + xOffset, -y + World.SIZE + yOffset);
				if (pixel == 0x000000FF) world.addEntity(new WallEntity(game, x, y));
				else if (pixel == 0x00FF00FF) world.addEntity(new CoinEntity(game, x, y));
				else if (pixel == 0xFF0000FF) world.addEntity(new EnemyEntity(game, x, y));
				else if ((pixel & 0xFFFFFF00) == 0x0000FF00) world.addEntity(new DoorEntity(game, x, y, pixel & 0xFF));
				else if ((pixel & 0xFFFFFF00) == 0xFFFF0000) {
					world.addEntity(new PrePortalEntity(game, x, y, pixel & 0xFF));
				} else if (pixel == 0xFF8000FF) world.addEntity(new LadderEntity(game, x, y));
				else if (pixel == 0x00FFFFFF) world.addEntity(new SwitchEntity(game, x, y));
				else if (pixel == 0xFF00FFFF) world.addEntity(new MachineEntity(game, x, y));
			}
		}
		
		return world;
	}
	
	@Override
	public void update() {
		world.update();
		
		PortalEntity portal = world.getTargetPortal();
		if (portal != null && portal.getZoomTimer() == PortalEntity.ZOOM_TIME) manager.transitionTo(new GameState(game,
				portal.getNextWorld(), world.getPlayer()), new Transition.FadeTransition(.5f));
		
		if (world.shouldGoUp() && world.getUpperWorld() != null) {
//			manager.transitionTo(new GameState(game, world.getUpperWorld(), world.getPlayer()),
//					new Transition.FadeTransition(.5f));
			manager.setState(new GameState(game, world.getUpperWorld(), world.getPlayer()));
		}
		
		if (shakeTicks > 0) shakeTicks--;
		if (world.getPlayer().isInvulnerable() && shakeTicks == 0) shakeTicks = SHAKE_TIME;
		
		if (world.getPlayer().isRemoved()) manager.transitionTo(new ImageState(game, true),
				new Transition.FadeTransition(1f));
	}
	
	@Override
	public void render(FrameBuffer target) {
		clearScreen(Color.DARK_GRAY);
		
		PortalEntity portal = world.getTargetPortal();
		PlayerEntity player = world.getPlayer();
		
		if (portal != null) {
			float alpha = (float) portal.getZoomTimer() / PortalEntity.ZOOM_TIME;
			player.setAlpha(1f - alpha);
			player.setScale(1f - alpha);
			gameCamera.position.set(tmp.set(0f, 0f).lerp(portal.pos, alpha), 0f);
			gameCamera.position.add(MathUtils.random() * shakeTicks / SHAKE_TIME, MathUtils.random() * shakeTicks
					/ SHAKE_TIME, 0f);
			gameCamera.zoom = Interpolation.linear.apply(World.SIZE + .5f, 0.5f, alpha * alpha);
		} else {
			gameCamera.position.set(0f, 0f, 0f);
			gameCamera.zoom = World.SIZE + 0.5f;
			
			player.setAlpha(1f);
			player.setScale(1f);
		}
		
		gameCamera.update();
		batch.setProjectionMatrix(gameCamera.combined);
		
		batch.begin();
		world.render(batch, true);
		
		batch.setProjectionMatrix(uiCamera.combined);
		
		batch.draw(vignette, 0f, 0f, uiCamera.viewportWidth, 1f);
		
		for (int i = 0; i < player.getMaxHealth(); i++) {
			if (i < player.getHealth()) batch.draw(heart, i * ICON_SCALE * 0.5f + 0.01f, 0.01f, ICON_SCALE, ICON_SCALE);
			else batch.draw(heartGrey, i * ICON_SCALE * 0.5f + 0.01f, 0.01f, ICON_SCALE, ICON_SCALE);
		}
		
		for (int i = 0; i < player.getCoins(); i++) {
			batch.draw(coin, 0.01f, i * ICON_SCALE * 0.125f + ICON_SCALE, ICON_SCALE, ICON_SCALE);
		}
		
		batch.end();
	}
	
	public boolean isZooming() {
		PortalEntity portal = world.getTargetPortal();
		
		return portal == null ? false : portal.getZoomTimer() != 0;
	}
	
	@Override
	public void hide() {
		world.hide();
	}
	
	@Override
	public void resize(boolean creation, int width, int height) {
		gameCamera.viewportWidth = 2f * width / height;
		gameCamera.viewportHeight = 2f;
		
		uiCamera.setToOrtho(false, (float) width / height, 1f);
		uiCamera.update();
	}
}
