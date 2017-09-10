package rf.androidovshchik.vkadvancedposting;

import android.app.Application;

import com.vk.sdk.VKSdk;

import rf.androidovshchik.vkadvancedposting.components.AccessTokenTracker;
import rf.androidovshchik.vkadvancedposting.components.CrashReportingTree;
import timber.log.Timber;

/**
 * Developer: Vlad Kalyuzhnyu
 * Email:     gimrcpp@gmail.com
 */
public class VKAdvancedPosting extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        new AccessTokenTracker().startTracking();
        VKSdk.initialize(getApplicationContext());
    }
}
