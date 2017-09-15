package rf.androidovshchik.vkadvancedposting.models;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class BackgroundBottom extends Background {

    private static final String TAG = BackgroundBottom.class.getSimpleName();

    public BackgroundBottom(int index) {
        super(index);
        GdxLog.d(TAG, "BackgroundBottom index: %d", index);
    }

    @Override
    protected void bind(int worldWidth, int worldHeight) {

    }
}
