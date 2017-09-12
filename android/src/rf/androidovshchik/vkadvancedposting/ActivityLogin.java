package rf.androidovshchik.vkadvancedposting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vk.sdk.VKSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rf.androidovshchik.vkadvancedposting.components.VKLoginCallback;
import rf.androidovshchik.vkadvancedposting.events.VKLoginEvent;

public class ActivityLogin extends AppCompatActivity {

    @BindView(R.id.actionLogin)
    View actionLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.actionLogin)
    public void onLogin() {
        actionLogin.setEnabled(false);
        VKSdk.login(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onVKLoginEvent(VKLoginEvent event) {
        if (event.isLogged) {
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            actionLogin.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED, new Intent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKLoginCallback());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
