package rf.androidovshchik.vkadvancedposting;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import rf.androidovshchik.vkadvancedposting.events.clicks.PhotoClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.StickerClickEvent;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.layout.MainLayout;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.photos.PhotosRecyclerView;

public abstract class ActivityMainWorld extends ActivityMainBase {

	@BindView(R.id.mainLayout)
	public MainLayout mainLayout;

	protected int windowHeight;
	protected Rect rectResizedWindow;

	protected FragmentWorld fragmentWorld;
	protected FragmentPostText fragmentPostText;

	protected PopupWindow photosPopup;
	protected PopupWindow stickersPopup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		windowHeight = ViewUtil.getWindow(getApplicationContext()).y;
		rectResizedWindow = new Rect();
		fragmentWorld = new FragmentWorld();
		fragmentPostText = new FragmentPostText();
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
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onStickerClickEvent(StickerClickEvent event) {
		fragmentWorld.world.postRunnable("addSticker", event.position + 1);
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onPhotoClickEvent(PhotoClickEvent event) {
		String sdcardPath = Environment.getExternalStorageDirectory().getPath() + "/";
		String path = ((PhotosRecyclerView) photosPopup.getContentView()).adapterPhotos
				.photoPaths.get(event.position).getPath().substring(sdcardPath.length());
		fragmentWorld.world.postRunnable("setPhotoBackground", path);
	}
}
