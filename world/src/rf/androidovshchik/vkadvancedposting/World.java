package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import rf.androidovshchik.vkadvancedposting.callbacks.StickerPressCallback;
import rf.androidovshchik.vkadvancedposting.components.GdxLog;
import rf.androidovshchik.vkadvancedposting.callbacks.StickersDragCallback;
import rf.androidovshchik.vkadvancedposting.models.Background;
import rf.androidovshchik.vkadvancedposting.models.Player;
import rf.androidovshchik.vkadvancedposting.models.Sticker;

public class World extends WorldAdapter {

	public static final String TAG = World.class.getSimpleName();

	private static final int SECOND_FINGER = 1;

	private OrthographicCamera camera;
	private FitViewport viewport;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch spriteBatch;

	private Stage backgroundStage;
	private Stage stickersStage;
	//private TopCurtain worldTopCurtain;
	//private BottomCurtain worldBottomCurtain;
	private StickersDragCallback stickersDragCallback;
	private StickerPressCallback stickerPressCallback;

	private boolean drawGradient = false;
	private Color gradientTopLeftColor = Color.CLEAR;
	private Color gradientBottomRightColor = Color.CLEAR;
	private Color gradientBlendedColor = Color.CLEAR;

	public World(boolean debug, int worldWidth, int worldHeight) {
		GdxLog.DEBUG = debug;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.worldHalfDifference = (worldHeight - worldWidth) / 2;
	}

	@Override
	public void create() {
		GdxLog.print(TAG, "lifecycle create");
		GdxLog.d(TAG, "worldWidth: %d", worldWidth);
		GdxLog.d(TAG, "worldHeight: %d", worldHeight);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, worldWidth, worldHeight);
		viewport = new FitViewport(worldWidth, worldHeight, camera);

		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();

		backgroundStage = new Stage(viewport);
		stickersStage = new Stage(viewport);
		stickersDragCallback = new StickersDragCallback();

		GestureDetector gestureDetector = new GestureDetector(this);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gestureDetector);
		inputMultiplexer.addProcessor(stickersStage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render() {
		GdxLog.print(TAG, "lifecycle render");
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		spriteBatch.setProjectionMatrix(camera.combined);
		if (drawGradient) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.rect(0, 0, worldWidth, worldHeight, gradientBlendedColor,
					gradientBottomRightColor, gradientBlendedColor, gradientTopLeftColor);
			shapeRenderer.end();
		} else {
			spriteBatch.begin();
			backgroundStage.getRoot().draw(spriteBatch, 1);
			spriteBatch.end();
		}
		int worldSizeDifference = Math.round(1f * (worldHeight - worldWidth) / 2);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, worldSizeDifference, worldWidth, worldWidth,
				Color.OLIVE, Color.OLIVE, Color.OLIVE, Color.OLIVE);
		shapeRenderer.end();
		spriteBatch.begin();
		stickersStage.act();
		stickersStage.getRoot().draw(spriteBatch, 1);
		spriteBatch.end();

		if (rendersCount >= 2) {
			rendersCount = 0;
			Gdx.graphics.setContinuousRendering(false);
		} else {
			Gdx.graphics.setContinuousRendering(true);
			rendersCount++;
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector2 coordinates = stickersStage.screenToStageCoordinates(new Vector2(x, y));
		GdxLog.f(TAG, "touchDown pointer1 x: %f y: %f", coordinates.x, coordinates.y);
		if (pointer == SECOND_FINGER) {
			Sticker sticker = getCurrentSticker();
			if (sticker != null) {
				sticker.isPinching = true;
				sticker.setPinchStarts(coordinates.x, coordinates.y);
			}
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		if (stickerPressCallback == null) {
			return false;
		}
		Sticker sticker = getCurrentSticker();
		if (sticker != null) {
			stickerPressCallback.onStickerLongPressed();
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
						  Vector2 pointer2) {
		// initialPointer doesn't change
		// all vectors contains device coordinates
		Sticker sticker = getCurrentSticker();
		if (sticker == null) {
			return false;
		}

		Vector2 startVector = new Vector2(initialPointer1).sub(initialPointer2);
		Vector2 currentVector = new Vector2(pointer1).sub(pointer2);

		float startAngle = (float) Math.toDegrees(Math.atan2(startVector.y, startVector.x));
		float endAngle = (float) Math.toDegrees(Math.atan2(currentVector.y, currentVector.x));

		Vector2 startCenter = new Vector2(startVector).scl(0.5f).add(initialPointer2);
		Vector2 currentCenter = new Vector2(currentVector).scl(0.5f).add(pointer2);
		Vector2 rawTrans = new Vector2(currentCenter).sub(startCenter);

		sticker.onPinch(rawTrans.x, rawTrans.y, sticker.startScale * currentVector.len() /
				startVector.len(), sticker.startRotation + endAngle - startAngle);
		return false;
	}

	@Override
	public void pinchStop() {
		GdxLog.print(TAG, "pinchStop");
		Sticker sticker = getCurrentSticker();
		if (sticker != null) {
			sticker.isPinching = false;
		}
	}

	@Override
	public void resize(int width, int height) {
		GdxLog.print(TAG, "lifecycle resize");
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		GdxLog.print(TAG, "lifecycle dispose");
		backgroundStage.dispose();
		stickersStage.dispose();
		spriteBatch.dispose();
		shapeRenderer.dispose();
	}

	public void setStickerPressCallback(StickerPressCallback stickerPressCallback) {
		this.stickerPressCallback = stickerPressCallback;
	}

	public void setModeImmediately(boolean isPostMode) {
		this.isPostMode = isPostMode;

	}

	public void setGradientBackground(String topLeftColor, String bottomRightColor) {
		gradientTopLeftColor = parseColor(topLeftColor);
		gradientBottomRightColor = parseColor(bottomRightColor);
		gradientBlendedColor = new Color(gradientTopLeftColor).add(gradientBottomRightColor);
		drawGradient = true;
		Gdx.graphics.requestRendering();
	}

	@SuppressWarnings("unused")
	public void setImagesBackground(String topPath, String centerPath, String bottomPath) {
		for(int i = backgroundStage.getActors().size - 1; i >= 0; i--) {
			backgroundStage.getActors().get(i).remove();
		}
		Texture texture = new Texture(Gdx.files.internal("illustrations/" + centerPath));
		Background backgroundCenter = new Background(texture,
				Player.TYPE_CENTER_BACKGROUND, centerPath);
		backgroundCenter.setup(worldWidth, worldHeight);
		drawGradient = false;
		backgroundStage.addActor(backgroundCenter);
	}

	@SuppressWarnings("unused")
	public void addSticker(Integer filename) {
		Texture texture = new Texture(Gdx.files.internal("stickers/" + filename + ".png"));
		Sticker sticker = new Sticker(texture, Player.TYPE_STICKER, String.valueOf(filename),
				worldWidth, worldHeight);
		sticker.addListener(stickersDragCallback);
		stickersStage.addActor(sticker);
	}

	@SuppressWarnings("unused")
	public void removeSticker() {
		// TODO remove
		for (int i = 0; i < stickersStage.getActors().size; i++) {
			Sticker sticker = (Sticker) stickersStage.getActors().get(i);
		}
	}

	private Sticker getCurrentSticker() {
		/*int index = stickersDragCallback.index;
		if (index != Sticker.NONE && index < stickersStage.getActors().size) {
			return (Sticker) stickersStage.getActors().get(stickersDragCallback.index);
		}*/
		return null;
	}
}