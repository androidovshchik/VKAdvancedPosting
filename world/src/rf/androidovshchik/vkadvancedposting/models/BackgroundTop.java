package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundTop extends Player {

    private static final String TAG = BackgroundTop.class.getSimpleName();

    public BackgroundTop(int index, Texture texture) {
        super(index, texture);
        GdxLog.d(TAG, "BackgroundTop index: %d", index);
    }
}
