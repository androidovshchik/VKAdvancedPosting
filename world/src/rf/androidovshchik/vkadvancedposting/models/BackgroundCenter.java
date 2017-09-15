package rf.androidovshchik.vkadvancedposting.models;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundCenter extends Background {

    private static final String TAG = BackgroundCenter.class.getSimpleName();

    public BackgroundCenter(int index) {
        super(index);
        GdxLog.d(TAG, "BackgroundCenter index: %d", index);
    }

    @Override
    protected void bind(int worldWidth, int worldHeight) {
        setPosition(worldWidth / 2 - getWidth() / 2, worldHeight / 2 - getHeight() / 2);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(1f * worldHeight / getHeight());
    }
}
