package rf.androidovshchik.vkadvancedposting;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rf.androidovshchik.vkadvancedposting.components.VKTokenTracker;
import rf.androidovshchik.vkadvancedposting.components.CrashReportingTree;
import rf.androidovshchik.vkadvancedposting.events.VKLoginEvent;
import timber.log.Timber;

/**
 * Developer: Vlad Kalyuzhnyu
 * Email:     gimrcpp@gmail.com
 */
public class VKAdvancedPosting extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        new VKTokenTracker().startTracking();
        VKSdk.initialize(getApplicationContext());
        onVKLoginEvent(new VKLoginEvent(!VKSdk.wakeUpSession(getApplicationContext(), null)));
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onVKLoginEvent(VKLoginEvent event) {
        if (event.needLogin) {
            Timber.d("onVKLoginEvent needLogin");
            getApplicationContext().startActivity(new Intent(getApplicationContext(),
                    ActivityLogin.class));
        }
    }
}
