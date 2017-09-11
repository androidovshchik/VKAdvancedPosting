package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class VKAdvancedPostGenerator extends PostGeneratorAdapter {

	public static final String TAG = VKAdvancedPostGenerator.class.getSimpleName();

	private SpriteBatch batch;

	private Stage stage;

	private StickersDragListener stickersDragListener;

	@Override
	public void create() {
		Gdx.app.log(TAG, "Width: " + Gdx.graphics.getWidth());
		Gdx.app.log(TAG, "Height: " + Gdx.graphics.getHeight());

		batch = new SpriteBatch();

		stage = new Stage();
		stickersDragListener = new StickersDragListener();
		addSticker(1, 500, 500, 45);
		addSticker(2, 400, 10, 0);
		addSticker(3, 10, 200, -45);
		addSticker(4, 10, 300, 90);
		addSticker(5, 100, 10, 0);

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

		batch.begin();
		stage.getRoot().draw(batch, 1);
		batch.end();

		Gdx.graphics.setContinuousRendering(false);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (pointer == 1) {
			Gdx.app.log(TAG, "touchDown pointer1 x: " + x + " y: " + y);
			Sticker sticker = (Sticker) stage.getActors().get(stickersDragListener.stickerIndex);
			sticker.startScale = sticker.getScaleX();
		}
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (stickersDragListener.stickerIndex != StickersDragListener.NONE) {
			Gdx.app.log(TAG, "zoom initialDistance: " + initialDistance + " distance: " + distance);
			Sticker sticker = (Sticker) stage.getActors().get(stickersDragListener.stickerIndex);
			float pointerScale = distance / initialDistance;
			float targetScale = sticker.startScale * pointerScale;
			sticker.setScale(targetScale);
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
						  Vector2 pointer2) {
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
		return false;
	}

	@Override
	public void pinchStop() {
		if (stickersDragListener.stickerIndex != StickersDragListener.NONE) {
			Gdx.app.log(TAG, "pinchStop");
			Sticker sticker = (Sticker) stage.getActors().get(stickersDragListener.stickerIndex);
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	public void addSticker(int index, float x, float y, float rotation) {
		Texture texture = new Texture(Gdx.files.internal("stickers/" + index + ".png"));
		Sticker sticker = new Sticker(texture);
		sticker.setPosition(x, y);
		sticker.setRotation(rotation);
		sticker.addListener(stickersDragListener);
		sticker.setUserObject(stage.getActors().size);
		stage.addActor(sticker);
	}

	public void removeSticker() {
		// TODO remove
		for (int i = 0; i < stage.getActors().size; i++) {
			stage.getActors().get(i).setUserObject(i);
		}
	}
}