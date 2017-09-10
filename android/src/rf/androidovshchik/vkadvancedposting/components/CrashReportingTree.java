package rf.androidovshchik.vkadvancedposting.components;

import android.util.Log;

import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable throwable) {
        if (throwable != null) {
            Log.e(getClass().getSimpleName(), throwable.getMessage());
        }
    }
}