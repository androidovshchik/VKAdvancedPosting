package rf.androidovshchik.vkadvancedposting;

import android.graphics.Rect;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.OnClick;
import rf.androidovshchik.vkadvancedposting.views.layout.MainLayout;

public abstract class ActivityMainLayouts extends ActivityMainBase {

	@BindView(R.id.mainLayout)
	public MainLayout mainLayout;

	protected FragmentWorld fragmentWorld;
	protected FragmentPostText fragmentPostText;

    private DialogWallPost dialogWallPost;

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

	@OnClick(R.id.actionSend)
	public void onSend() {
		dialogWallPost.show(getSupportFragmentManager(), DialogWallPost.class.getSimpleName());
	}
}
