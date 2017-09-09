package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class VKAdvancedPostGenerator extends ApplicationAdapter {

	private SpriteBatch batch;
	private ArrayList<Sprite> stickers;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		stickers = new ArrayList<Sprite>();
		addSticker(1, 10, 10, 45);
		addSticker(2, 400, 10, 0);
		addSticker(3, 10, 200, -45);
		addSticker(4, 10, 300, 90);
		addSticker(5, 100, 10, 0);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (Sprite sticker : stickers) {
			sticker.draw(batch);
		}
		batch.end();
		Gdx.graphics.setContinuousRendering(false);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		for (Sprite sticker : stickers) {
			sticker.getTexture().dispose();
		}
	}

	public void addSticker(int index, float x, float y, float rotation) {
		Texture texture1 = new Texture(Gdx.files.internal("stickers/" + index + ".png"));
		Sprite sprite1 = new Sprite(texture1);
		sprite1.setPosition(x, y);
		sprite1.setRotation(rotation);
		stickers.add(sprite1);
	}
}
