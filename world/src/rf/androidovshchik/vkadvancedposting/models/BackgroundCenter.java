package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundCenter extends Background {

    private static final String TAG = BackgroundCenter.class.getSimpleName();

    public BackgroundCenter(int index, Texture texture) {
        super(index, texture);
        GdxLog.d(TAG, "BackgroundCenter index: %d", index);
    }

    @Override
    public void bind(int worldWidth, int worldHeight) {
        setPosition(worldWidth / 2 - getWidth() / 2, worldHeight / 2 - getHeight() / 2);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(1f * worldHeight / getHeight());
    }
}
