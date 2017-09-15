package rf.androidovshchik.vkadvancedposting.models;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundTop extends Background {

    private static final String TAG = BackgroundTop.class.getSimpleName();

    public BackgroundTop(int index) {
        super(index);
        GdxLog.d(TAG, "BackgroundTop index: %d", index);
    }

    @Override
    protected void bind(int worldWidth, int worldHeight) {

    }
}
