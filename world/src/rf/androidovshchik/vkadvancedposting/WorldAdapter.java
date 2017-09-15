package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public abstract class WorldAdapter extends GestureDetector.GestureAdapter
        implements ApplicationListener {

    private static final String TAG = WorldAdapter.class.getSimpleName();

    @Override
    public void pause() {
        GdxLog.print(TAG, "pause");
    }

    @Override
    public void resume() {
        GdxLog.print(TAG, "resume");
    }

    protected static Color parseColor(String hex) {
        String s1 = hex.substring(0, 2);
        int v1 = Integer.parseInt(s1, 16);
        float f1 = 1f * v1 / 255f;
        String s2 = hex.substring(2, 4);
        int v2 = Integer.parseInt(s2, 16);
        float f2 = 1f * v2 / 255f;
        String s3 = hex.substring(4, 6);
        int v3 = Integer.parseInt(s3, 16);
        float f3 = 1f * v3 / 255f;
        return new Color(f1, f2, f3, 1f);
    }
}
