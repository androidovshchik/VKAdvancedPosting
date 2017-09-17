package rf.androidovshchik.vkadvancedposting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.vk.sdk.VKSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import rf.androidovshchik.vkadvancedposting.events.VKInvalidTokenEvent;

public abstract class ActivityMainBase extends AppCompatActivity
		implements AndroidFragmentApplication.Callbacks, ViewTreeObserver.OnGlobalLayoutListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		if (!VKSdk.isLoggedIn()) {
			startActivityForResult(new Intent(getApplicationContext(), ActivityLogin.class), 1);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@SuppressWarnings("unused")
	@Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
	public void onVKInvalidTokenEvent(VKInvalidTokenEvent event) {

	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}
}
