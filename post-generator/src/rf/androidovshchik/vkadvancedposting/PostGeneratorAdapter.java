package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

public abstract class PostGeneratorAdapter extends GestureDetector.GestureAdapter
        implements ApplicationListener {

    private static final String TAG = PostGeneratorAdapter.class.getSimpleName();

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume");
    }
}
