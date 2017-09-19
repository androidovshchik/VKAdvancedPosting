package rf.androidovshchik.vkadvancedposting;

import android.graphics.Rect;
import android.os.Bundle;

import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import rf.androidovshchik.vkadvancedposting.events.VKResponseEvent;
import rf.androidovshchik.vkadvancedposting.views.layout.MainLayout;

public abstract class ActivityMainLayouts extends ActivityMainBase {

	@BindView(R.id.mainLayout)
	public MainLayout mainLayout;

	protected FragmentWorld fragmentWorld;
	protected FragmentPostText fragmentPostText;

	protected DialogWallPost dialogWallPost;

	protected Rect rectResizedWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentWorld = new FragmentWorld();
		fragmentPostText = new FragmentPostText();
		dialogWallPost = new DialogWallPost();
		rectResizedWindow = new Rect();
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.world, fragmentWorld, FragmentWorld.class.getSimpleName())
				.add(R.id.world, fragmentPostText, FragmentPostText.class.getSimpleName())
				.commitAllowingStateLoss();
	}

	@Override
	public void onGlobalLayout() {
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rectResizedWindow);
		mainLayout.onViewportChange(rectResizedWindow.bottom);
	}

	@OnClick(R.id.actionPost)
	public void onPost() {
		mainLayout.onPostMode();
	}

	@OnClick(R.id.actionHistory)
	public void onHistory() {
		mainLayout.onHistoryMode();
	}

	@SuppressWarnings("unused")
	@Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
	public void onVKResponseEvent(VKResponseEvent event) {
		if (event.isSuccessful) {
			if (event.isPhotoUploadRequest) {
				makeWallPost(new VKAttachments(((VKPhotoArray) event.parsedModel).get(0)));
			} else {
				dialogWallPost.wallPostLayout.onPublishSucceed();
			}
		} else {
			dialogWallPost.wallPostLayout.onPublishFailed();
		}
	}
}
