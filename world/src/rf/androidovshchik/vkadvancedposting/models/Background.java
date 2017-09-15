package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class Background extends Player {

    private static final String TAG = Background.class.getSimpleName();

    public Background(int index, Texture texture) {
        super(index, texture);
        GdxLog.d(TAG, "Background index: %d", index);
    }
}
