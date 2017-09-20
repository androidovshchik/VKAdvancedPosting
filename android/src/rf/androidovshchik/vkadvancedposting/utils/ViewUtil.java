package rf.androidovshchik.vkadvancedposting.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

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

    @SuppressWarnings("unused")
    public static PopupWindow createPopup(View view, @StyleRes int style) {
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setAnimationStyle(style);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(false);
        return popupWindow;
    }

    @SuppressWarnings("unused")
    public static void showPopup(PopupWindow popupWindow, View parent) {
        if (popupWindow == null) {
            return;
        }
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }

    @SuppressWarnings("unused")
    public static void hidePopup(PopupWindow popupWindow) {
        if (popupWindow == null) {
            return;
        }
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @SuppressWarnings("unused")
    public static void showKeyboard(Context context) {
        ((InputMethodManager) context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @SuppressWarnings("unused")
    public static void hideKeyboard(Context context) {
        ((InputMethodManager) context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    @SuppressWarnings("unused")
    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @SuppressWarnings("unused")
    public static Bitmap overlayBitmapToCenter(Bitmap bitmap, Bitmap overlay) {
        try {
            float marginLeft = bitmap.getWidth() * 0.5f - overlay.getWidth() * 0.5f;
            float marginTop = bitmap.getHeight() * 0.5f - overlay.getHeight() * 0.5f;
            Bitmap overlayBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(overlayBitmap);
            canvas.drawBitmap(bitmap, new Matrix(), null);
            canvas.drawBitmap(overlay, marginLeft, marginTop, null);
            return overlayBitmap;
        } finally {
            bitmap.recycle();
            overlay.recycle();
        }
    }
}
