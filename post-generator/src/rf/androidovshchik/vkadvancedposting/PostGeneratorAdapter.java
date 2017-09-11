package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.input.GestureDetector;

public abstract class PostGeneratorAdapter extends GestureDetector.GestureAdapter
        implements ApplicationListener {

    private static final String TAG = PostGeneratorAdapter.class.getSimpleName();

    @Override
    public void pause() {
        GdxLog.f(TAG, "pause");
    }

    @Override
    public void resume() {
        GdxLog.f(TAG, "resume");
    }
}
