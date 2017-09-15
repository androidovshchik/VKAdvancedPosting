package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    // Null may be only String params
    public void postRunnable(final String name, final Object... params) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Method method = null;
                Class[] classes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    classes[i] = params[i] == null ? String.class : params[i].getClass();
                }
                try {
                    method = World.class.getMethod(name, classes);
                } catch (SecurityException e) {
                    GdxLog.print(TAG, e.toString());
                } catch (NoSuchMethodException e) {
                    GdxLog.print(TAG, e.toString());
                }
                if (method == null) {
                    return;
                }
                try {
                    method.invoke(WorldAdapter.this, params);
                } catch (IllegalArgumentException e) {
                    GdxLog.print(TAG, e.toString());
                } catch (IllegalAccessException e) {
                    GdxLog.print(TAG, e.toString());
                } catch (InvocationTargetException e) {
                    GdxLog.print(TAG, e.toString());
                }
            }
        });
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
