package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundBottom extends Background {

    private static final String TAG = BackgroundBottom.class.getSimpleName();

    public BackgroundBottom(int index, Texture texture) {
        super(index, texture);
        GdxLog.d(TAG, "BackgroundBottom index: %d", index);
    }

    @Override
    public void bind(int worldWidth, int worldHeight) {

    }
}
