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

public class VKAdvancedPostGenerator extends PostGeneratorAdapter {

	public static final String TAG = VKAdvancedPostGenerator.class.getSimpleName();

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
				sticker.setPinchStarts();
			}
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
						  Vector2 pointer2) {
		pointer2 = stickers.screenToStageCoordinates(pointer2);
		GdxLog.print(TAG, "pinch" + pointer2.toString());
		Sticker sticker = getCurrentSticker();
		if (sticker == null) {
			return false;
		}
		/*var startCenter = initialPointer1.sub(pointer1).scl(0.5f) + point#1.start
		var currentCenter = (point#0.current - point#1.current)/2 + point#1.current
		var rawTrans = currentCenter - startCenter
		var startCenter = (point#0.start - point#1.start)/2 + point#1.start
		var currentCenter = (point#0.current - point#1.current)/2 + point#1.current
		var rawTrans = currentCenter - startCenter*/
		//Sticker sticker = (Sticker) stickers.getActors().get(stickersDragListener.sticker);
		/*Vector2 startVector = initialPointer1.sub(pointer1);
		Vector2 currentVector = initialPointer2.sub(pointer2);
		//double startAngle = Math.atan2(startVector.y, startVector.x);
		//double endAngle = Math.atan2(currentVector.y, currentVector.x);
		//double deltaAngle = endAngle - startAngle;
		//Gdx.app.log(TAG, "pinch deltaAngle: " + deltaAngle);
		float scale = currentVector.len() / startVector.len();
		Gdx.app.log(TAG, "pinch scale: " + scale + " stickerScale: " + sticker.getScaleX());
		if (!Float.isNaN(scale)) {
			sticker.setScale(scale);
		}
		pointer1.dst(pointer2)*/
		//Sticker sticker = (Sticker) stage.getActors().get(stickersDragListener.stickerIndex);
		/*Vector2 startVector = initialPointer1.sub(pointer1);
		Vector2 currentVector = initialPointer2.sub(pointer2);
		//double startAngle = Math.atan2(startVector.y, startVector.x);
		//double endAngle = Math.atan2(currentVector.y, currentVector.x);
		//double deltaAngle = endAngle - startAngle;
		//Gdx.app.log(TAG, "pinch deltaAngle: " + deltaAngle);
		float scale = currentVector.len() / startVector.len();
		Gdx.app.log(TAG, "pinch scale: " + scale + " stickerScale: " + sticker.getScaleX());
		if (!Float.isNaN(scale)) {
			sticker.setScale(scale);
		}
		pointer1.dst(pointer2)*/
		/*
		* Gdx.app.log(TAG, "zoom initialDistance: " + initialDistance + " distance: " + distance);
			Sticker sticker = (Sticker) stage.getActors().get(stickersDragListener.stickerIndex);
			float pointerScale = distance / initialDistance;
			float targetScale = sticker.startScale * pointerScale;
			sticker.setScale(targetScale);*/
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
		//background.getViewport().update(width, height, true);
		//stickers.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		GdxLog.print(TAG, "dispose");
		background.dispose();
		stickers.dispose();
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