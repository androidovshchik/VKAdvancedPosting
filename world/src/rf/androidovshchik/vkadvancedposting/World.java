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
import rf.androidovshchik.vkadvancedposting.models.BackgroundCenter;
import rf.androidovshchik.vkadvancedposting.models.Sticker;

public class World extends WorldAdapter {

	public static final String TAG = World.class.getSimpleName();

	private static final int SECOND_FINGER = 1;

	private final int windowHeight;

	private final int worldWidth;
	private final int worldHeight;

	private boolean drawGradient = false;
	private Color gradientTopLeftColor = Color.CLEAR;
	private Color gradientBottomRightColor = Color.CLEAR;
	private Color gradientBlendedColor = Color.CLEAR;

	private OrthographicCamera camera;
	private FitViewport viewport;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	private Stage stage;
	private StickersDragCallback stickersDragCallback;
	private StickerPressCallback stickerPressCallback;

	public World(int windowHeight, int worldWidth, int worldHeight) {
		this.windowHeight = windowHeight;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
	}

	@Override
	public void create() {
		GdxLog.DEBUG = true;
		GdxLog.print(TAG, "create");
		GdxLog.d(TAG, "windowHeight: %d", windowHeight);
		GdxLog.d(TAG, "worldWidth: %d", worldWidth);
		GdxLog.d(TAG, "worldHeight: %d", worldHeight);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, worldWidth, worldHeight);
		//camera.zoom = 1f * worldHeight / windowHeight;
		GdxLog.print(TAG, "zoom: " + camera.zoom);
		viewport = new FitViewport(worldWidth, worldHeight, camera);

		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		stage = new Stage(viewport);
		stickersDragCallback = new StickersDragCallback();
		setImageBackground("illustrations/stars/bg_stars_center.png");
		addSticker(5, 200, 200, 1, 0);

		GestureDetector gestureDetector = new GestureDetector(this);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gestureDetector);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.getActors().get(0).setVisible(!drawGradient);

		if (drawGradient) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.rect(0, 0, worldWidth, worldHeight, gradientBlendedColor,
					gradientBottomRightColor, gradientBlendedColor, gradientTopLeftColor);
			shapeRenderer.end();
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.getRoot().draw(batch, 1);
		batch.end();

		Gdx.graphics.setContinuousRendering(false);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (pointer == SECOND_FINGER) {
			Vector2 coordinates = stage.screenToStageCoordinates(new Vector2(x, y));
			GdxLog.f(TAG, "touchDown pointer1 x: %f y: %f", coordinates.x, coordinates.y);
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
		GdxLog.print(TAG, "resize");
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		GdxLog.print(TAG, "dispose");
		stage.dispose();
		batch.dispose();
	}

	private Sticker getCurrentSticker() {
		int index = stickersDragCallback.index;
		if (index != Sticker.NONE && index < stage.getActors().size) {
			return (Sticker) stage.getActors().get(stickersDragCallback.index);
		}
		return null;
	}

	public void setStickerPressCallback(StickerPressCallback stickerPressCallback) {
		this.stickerPressCallback = stickerPressCallback;
	}

	public void notify(int topMargin, int viewportHeight, int worldHeight) {
		GdxLog.print(TAG, "notify");
		camera.translate(0, 100, 0);
		camera.zoom = 5f;
		camera.update();
		Gdx.graphics.requestRendering();
	}

	public void setImageBackground(String path) {
		drawGradient = false;
		Texture texture = new Texture(Gdx.files.internal(path));
		BackgroundCenter backgroundCenter = new BackgroundCenter(stage.getActors().size, texture);
		//backgroundCenter.setSize(1f, 1f * backgroundCenter.getHeight() / backgroundCenter.getWidth());
		//backgroundCenter.setOrigin(backgroundCenter.getWidth() / 2, backgroundCenter.getHeight() / 2);
		backgroundCenter.setPosition(worldWidth / 2 - backgroundCenter.getWidth() / 2,
				worldHeight / 2 - backgroundCenter.getHeight() / 2);
		backgroundCenter.setOrigin(backgroundCenter.getWidth() / 2, backgroundCenter.getHeight() / 2);
		backgroundCenter.setScale(1f * worldHeight / backgroundCenter.getHeight());
		stage.addActor(backgroundCenter);
	}

	public void setGradientBackground(String colorTopLeft, String colorBottomRight) {
		GdxLog.print(TAG, "colorTopLeft " + colorTopLeft);
		GdxLog.print(TAG, "colorBottomRight " + colorBottomRight);
		gradientTopLeftColor = parseColor(colorTopLeft);
		gradientBottomRightColor = parseColor(colorBottomRight);
		gradientBlendedColor = new Color(gradientTopLeftColor).add(gradientBottomRightColor);
		drawGradient = true;
		Gdx.graphics.requestRendering();
	}

	public void addSticker(int filename, float x, float y, float scale, float rotation) {
		Texture texture = new Texture(Gdx.files.internal("stickers/" + filename + ".png"));
		Sticker sticker = new Sticker(stage.getActors().size, texture);
		sticker.setOrigin(sticker.getWidth() / 2, sticker.getHeight() / 2);
		sticker.setPosition(x, y);
		sticker.setScale(scale);
		sticker.setRotation(rotation);
		sticker.addListener(stickersDragCallback);
		stage.addActor(sticker);
	}

	public void removeSticker() {
		// TODO remove
		for (int i = 0; i < stage.getActors().size; i++) {
			Sticker sticker = (Sticker) stage.getActors().get(i);
			sticker.index = i;
		}
	}
}