package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public abstract class WorldAdapter extends InputAdapter
        implements ApplicationListener, GestureDetector.GestureListener {

    private static final String TAG = WorldAdapter.class.getSimpleName();

    @Override
    public void pause() {
        GdxLog.print(TAG, "lifecycle pause");
    }

    @Override
    public void resume() {
        GdxLog.print(TAG, "lifecycle resume");
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    // null may be only String params
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

    public Pixmap getScreenshot() {
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0,
                Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(),
                Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        return pixmap;
    }

    public boolean saveScreenshot(Pixmap pixmap) {
        try {
            PixmapIO.writePNG(Gdx.files.external("screenshot.png"), pixmap);
        } finally {
            pixmap.dispose();
        }
        return true;
    }

    protected Color parseColor(String hex) {
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
