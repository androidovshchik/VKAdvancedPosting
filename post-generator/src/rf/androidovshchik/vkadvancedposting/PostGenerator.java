package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;
import rf.androidovshchik.vkadvancedposting.components.StickersDragListener;
import rf.androidovshchik.vkadvancedposting.models.Sticker;

public class PostGenerator extends PostGeneratorAdapter {

	public static final String TAG = PostGenerator.class.getSimpleName();

	private static final int SECOND_FINGER = 1;

	private OrthographicCamera camera;

	private SpriteBatch batch;

	private Stage background;
	private Stage stickers;

	private StickersDragListener stickersDragListener;

	@Override
	public void create() {
		GdxLog.print(TAG, "create");
		GdxLog.DEBUG = true;

		int worldWidth = 1080;
		int worldHeight = 1080;

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, worldWidth, worldHeight);

		background = new Stage(new ScreenViewport(camera));
		changeBackground("illustrations/stars/bg_stars_center.png");

		stickers = new Stage(new FitViewport(worldWidth, worldHeight, camera));
		stickersDragListener = new StickersDragListener();
		addSticker(5, 200, 200, 1, 0);

		GestureDetector gestureDetector = new GestureDetector(this);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gestureDetector);
		inputMultiplexer.addProcessor(stickers);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.getRoot().draw(batch, 1);
		stickers.getRoot().draw(batch, 1);
		batch.end();

		Gdx.graphics.setContinuousRendering(false);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (pointer == SECOND_FINGER) {
			Vector2 coordinates = stickers.screenToStageCoordinates(new Vector2(x, y));
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
		background.getViewport().update(width, height, true);
		stickers.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		GdxLog.print(TAG, "dispose");
		stickers.dispose();
		background.dispose();
		batch.dispose();
	}

	public void changeBackground(String path) {
		Texture texture = new Texture(Gdx.files.internal(path));
		Image backgroundImage = new Image(texture);
		background.addActor(backgroundImage);
	}

	public Sticker getCurrentSticker() {
		int index = stickersDragListener.index;
		if (index != Sticker.NONE && index < stickers.getActors().size) {
			return (Sticker) stickers.getActors().get(stickersDragListener.index);
		}
		return null;
	}

	public void addSticker(int filename, float x, float y, float scale, float rotation) {
		Texture texture = new Texture(Gdx.files.internal("stickers/" + filename + ".png"));
		Sticker sticker = new Sticker(stickers.getActors().size, texture);
		sticker.setPosition(x, y);
		sticker.setScale(scale);
		sticker.setRotation(rotation);
		sticker.addListener(stickersDragListener);
		stickers.addActor(sticker);
	}

	public void removeSticker() {
		// TODO remove
		for (int i = 0; i < stickers.getActors().size; i++) {
			Sticker sticker = (Sticker) stickers.getActors().get(i);
			sticker.index = i;
		}
	}
}