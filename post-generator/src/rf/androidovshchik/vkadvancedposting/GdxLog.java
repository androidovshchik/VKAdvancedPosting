package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;

public class GdxLog {

    public static boolean DEBUG;

    @SuppressWarnings("all")
    public static void d(String tag, String message, Integer... values) {
        if (DEBUG) {
            Gdx.app.log(tag, String.format(message, values));
        }
    }

    @SuppressWarnings("all")
    public static void f(String tag, String message, Float... values) {
        if (DEBUG) {
            Gdx.app.log(tag, String.format(message.replaceAll("%f", "%.0f"), values));
        }
    }
}
