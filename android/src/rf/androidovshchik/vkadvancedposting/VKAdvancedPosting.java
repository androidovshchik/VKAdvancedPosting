package rf.androidovshchik.vkadvancedposting;

import android.app.Application;
import android.os.StrictMode;

import com.vk.sdk.VKSdk;

import rf.androidovshchik.vkadvancedposting.callbacks.VKTokenCallback;
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
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .penaltyDialog()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        new VKTokenCallback().startTracking();
        VKSdk.initialize(getApplicationContext());
    }
}
