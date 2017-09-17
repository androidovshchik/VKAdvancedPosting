package rf.androidovshchik.vkadvancedposting.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import timber.log.Timber;

public final class ViewUtil {

    @SuppressWarnings("unused")
    public static int dp2px(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @SuppressWarnings("unused")
    public static Point getWindow(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @SuppressWarnings("all")
    public static Point getScreen(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            Method methodGetRawHeight, methodGetRawWidth;
            try {
                methodGetRawHeight = Display.class.getMethod("getRawHeight");
                methodGetRawWidth = Display.class.getMethod("getRawWidth");
                size.y = (Integer) methodGetRawHeight.invoke(display);
                size.x = (Integer) methodGetRawWidth.invoke(display);
            } catch (NoSuchMethodException e) {
                Timber.e(e.getMessage());
            } catch (IllegalAccessException e) {
                Timber.e(e.getMessage());
            } catch (InvocationTargetException e) {
                Timber.e(e.getMessage());
            }
        }
        return size;
    }
}
