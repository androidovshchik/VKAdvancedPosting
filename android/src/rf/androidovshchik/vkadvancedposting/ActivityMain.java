package rf.androidovshchik.vkadvancedposting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.vk.sdk.VKSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rf.androidovshchik.vkadvancedposting.events.VKInvalidTokenEvent;
import rf.androidovshchik.vkadvancedposting.views.layout.TopToolbarLayout;

public class ActivityMain extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

	@BindView(R.id.topToolbarContainer)
	public TopToolbarLayout topToolbar;

	@BindView(R.id.stickersContainer)
	public ViewGroup stickersContainer;
	@BindView(R.id.stickersRecyclerView)
	public RecyclerView stickersRecyclerView;

	private FragmentPostGenerator postGenerator;

	private DialogWallPost dialogWallPost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		if (!VKSdk.isLoggedIn()) {
			startActivityForResult(new Intent(getApplicationContext(), ActivityLogin.class), 1);
		}
		setupPostGenerator();
		dialogWallPost = new DialogWallPost();
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

	private void setupPostGenerator() {
		postGenerator = (FragmentPostGenerator) getSupportFragmentManager().
				findFragmentByTag(FragmentPostGenerator.class.getSimpleName());
		if (postGenerator == null) {
			postGenerator = new FragmentPostGenerator();
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.postGenerator, postGenerator,
							FragmentPostGenerator.class.getSimpleName())
					.commitAllowingStateLoss();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(TopToolbarLayout.EXTRA_ACTIVE_POST, topToolbar.isActivePost);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		topToolbar.isActivePost = savedInstanceState.getBoolean(TopToolbarLayout.EXTRA_ACTIVE_POST);
	}

	@OnClick(R.id.actionFont)
	public void onFont() {
		dialogWallPost.show(getSupportFragmentManager(), DialogWallPost.class.getSimpleName());
	}

	@OnClick(R.id.actionPost)
	public void onPost() {
        topToolbar.onPostClicked();
    }

	@OnClick(R.id.actionHistory)
	public void onHistory() {
        topToolbar.onHistoryClicked();
	}

	@OnClick(R.id.actionSticker)
	public void onStickers() {
		BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(stickersContainer);
		switch (bottomSheetBehavior.getState()) {
			case BottomSheetBehavior.STATE_EXPANDED:
				bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				break;
			default:
				bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
				break;
		}
	}

    @OnClick(R.id.actionSend)
    public void onSend() {

    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		topToolbar.startSlideAnimation(true);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void exit() {}
}
