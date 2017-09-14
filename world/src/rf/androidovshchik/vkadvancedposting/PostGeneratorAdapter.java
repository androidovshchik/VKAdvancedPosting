package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.input.GestureDetector;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public abstract class PostGeneratorAdapter extends GestureDetector.GestureAdapter
        implements ApplicationListener {

    private static final String TAG = PostGeneratorAdapter.class.getSimpleName();

    @Override
    public void pause() {
        GdxLog.print(TAG, "pause");
    }

    @Override
    public void resume() {
        GdxLog.print(TAG, "resume");
    }
}
