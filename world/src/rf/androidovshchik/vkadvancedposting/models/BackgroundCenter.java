package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundCenter extends Player {

    private static final String TAG = BackgroundCenter.class.getSimpleName();

    public BackgroundCenter(int index, Texture texture) {
        super(index, texture);
        GdxLog.d(TAG, "BackgroundCenter index: %d", index);
    }
}
