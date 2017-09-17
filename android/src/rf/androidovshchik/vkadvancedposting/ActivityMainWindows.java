package rf.androidovshchik.vkadvancedposting;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import rf.androidovshchik.vkadvancedposting.events.clicks.ThemeClickEvent;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.layout.MainLayout;

public abstract class ActivityMainWindows extends ActivityMainWorld {

	@BindView(R.id.mainLayout)
	public MainLayout mainLayout;

	@BindView(R.id.popupShadow)
	View popupShadow;

	private PopupWindow photosPopup;
	private PopupWindow stickersPopup;
	private DialogWallPost dialogWallPost;

	private Rect rectResizedWindow;
	private int windowHeight;
	private int keyboardHeight;
	private boolean isKeyboardShowing;
	private boolean needShowPhotos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photosPopup = createPopup(R.layout.popup_photos);
		stickersPopup = createPopup(R.layout.popup_stickers);
		stickersPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		dialogWallPost = new DialogWallPost();
		rectResizedWindow = new Rect();
		windowHeight = ViewUtil.getWindow(getApplicationContext()).y;
		keyboardHeight = 0;
		isKeyboardShowing = false;
		needShowPhotos = false;
	}

	@Override
	public void onStart() {
		super.onStart();
		getWindow().getDecorView().getViewTreeObserver()
				.addOnGlobalLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rectResizedWindow);
		int difference = windowHeight - rectResizedWindow.bottom;
		if (difference != 0) {
			isKeyboardShowing = true;
			keyboardHeight = difference;
			photosPopup.setHeight(keyboardHeight);
			if (mainLayout.bottomToolbar.getY() > rectResizedWindow.bottom) {
				mainLayout.bottomToolbar.setY(windowHeight - keyboardHeight -
						mainLayout.bottomToolbar.getHeight());
			}
		} else {
			isKeyboardShowing = false;
			hidePhotosPopup();
			if (keyboardHeight != 0 &&
					mainLayout.bottomToolbar.getY() < windowHeight - keyboardHeight) {
				mainLayout.bottomToolbar.setY(windowHeight - mainLayout.bottomToolbar.getHeight());
			}
		}
	}

	@OnClick(R.id.actionSticker)
	public void onStickers() {
		showStickersPopup();
	}

	@OnTouch(R.id.popupShadow)
	public boolean onPopupShadowTouch() {
		hideStickersPopup();
		return false;
	}

	@SuppressWarnings("all")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onThemeClickEvent(ThemeClickEvent event) {
		showPhotosPopup();
	}

	@OnTouch(R.id.postText)
	public boolean onPostTextTouch() {
		hidePhotosPopup();
		return false;
	}

	@OnClick(R.id.actionSend)
	public void onSend() {
		dialogWallPost.show(getSupportFragmentManager(), DialogWallPost.class.getSimpleName());
	}

	private PopupWindow createPopup(@LayoutRes int id) {
		View view = View.inflate(getApplicationContext(), id, null);
		PopupWindow popupWindow = new PopupWindow(view);
		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setOutsideTouchable(false);
		return popupWindow;
	}

	private void showPhotosPopup() {
		if (!isKeyboardShowing) {
			mainLayout.bottomToolbar.setY(windowHeight - keyboardHeight -
					mainLayout.bottomToolbar.getHeight());
		}
		showPopup(photosPopup);
	}

	private void hidePhotosPopup() {
		hidePopup(photosPopup);
		if (!isKeyboardShowing) {
			mainLayout.bottomToolbar.setY(windowHeight - mainLayout.bottomToolbar.getHeight());
		}
	}

	private void showStickersPopup() {
		popupShadow.setVisibility(View.VISIBLE);
		needShowPhotos = photosPopup.isShowing() && !isKeyboardShowing;
		showPopup(stickersPopup);
	}

	private void hideStickersPopup() {
		popupShadow.setVisibility(View.GONE);
		hidePopup(stickersPopup);
		if (needShowPhotos) {
			mainLayout.post(new Runnable() {
				@Override
				public void run() {
					showPhotosPopup();
				}
			});
		}
	}

	private void showPopup(PopupWindow popupWindow) {
		if (!popupWindow.isShowing()) {
			popupWindow.showAtLocation(mainLayout, Gravity.BOTTOM, 0, 0);
		}
	}

	private void hidePopup(PopupWindow popupWindow) {
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		if (photosPopup.isShowing() && !isKeyboardShowing) {
			hidePhotosPopup();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		getWindow().getDecorView().getViewTreeObserver()
				.removeOnGlobalLayoutListener(this);
	}
}
