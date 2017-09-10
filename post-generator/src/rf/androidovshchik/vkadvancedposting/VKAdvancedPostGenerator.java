package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
	public void resize(int width, int height) {}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	public void addSticker(int index, float x, float y, float rotation) {
		Texture texture = new Texture(Gdx.files.internal("stickers/" + index + ".png"));
		Image image = new Image(texture);
		image.setPosition(x, y);
		image.setRotation(rotation);
		image.addListener(stickersDragListener);
		stage.addActor(image);
	}
}