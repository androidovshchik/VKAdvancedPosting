package rf.androidovshchik.vkadvancedposting;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import rf.androidovshchik.vkadvancedposting.events.clicks.ThemeClickEvent;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.PostEditText;
import rf.androidovshchik.vkadvancedposting.views.layout.MainLayout;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.themes.ThemesRecyclerView;

public abstract class ActivityMainWindows extends ActivityMainWorld {

	@BindView(R.id.mainLayout)
	public MainLayout mainLayout;

	@BindView(R.id.postText)
	public PostEditText postText;

	@BindView(R.id.popupShadow)
	View popupShadow;

	private PopupWindow photosPopup;
	private PopupWindow stickersPopup;
	private DialogWallPost dialogWallPost;

	private Rect rectResizedWindow;
	private int windowHeight;
	private int bottomToolbarHeight;
	private int keyboardHeight;
	private boolean isKeyboardShowing;
	// when keyboard is shown photosPopup don't disappear
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
		bottomToolbarHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_bottom_height);
		keyboardHeight = 0;
		isKeyboardShowing = false;
		needShowPhotos = false;
	}

	@Override
	public void onResume() {
		super.onResume();
		postText.requestFocus();
	}

	@Override
	public void onGlobalLayout() {
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rectResizedWindow);
		int difference = windowHeight - rectResizedWindow.bottom;
		if (difference != 0) {
			isKeyboardShowing = true;
			keyboardHeight = difference;
			photosPopup.setHeight(keyboardHeight);
			boolean firstTime = mainLayout.bottomToolbar.getLayoutParams().height ==
					bottomToolbarHeight;
			if (mainLayout.bottomToolbar.getY() > rectResizedWindow.bottom) {
				moveBottomToolbarUp(keyboardHeight);
				if (firstTime) {
					mainLayout.bottomToolbar.getLayoutParams().height =
							keyboardHeight + bottomToolbarHeight;
				}
			}
		} else {
			isKeyboardShowing = false;
			hidePhotosPopup();
			if (keyboardHeight != 0 &&
					mainLayout.bottomToolbar.getY() < windowHeight - keyboardHeight) {
				moveBottomToolbarDown();
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
		int position = event.position;
		if (position <= 0) {
			String white = "ffffff";
			fragment.world.setGradientBackground(white, white);
		} else if (position < 1 + ThemesRecyclerView.GRADIENT_DRAWABLES.length) {
			String topLeftColor = ThemesRecyclerView.GRADIENT_HEXES[position - 1][0];
			String bottomRightColor = ThemesRecyclerView.GRADIENT_HEXES[position - 1][1];
			fragment.world.setGradientBackground(topLeftColor, bottomRightColor);
		} else if (position >= 1 + ThemesRecyclerView.GRADIENT_DRAWABLES.length) {
			if (position >= 1 + ThemesRecyclerView.GRADIENT_DRAWABLES.length +
					ThemesRecyclerView.THUMB_PATHS.length) {
				if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
					requirePermission(Manifest.permission.READ_EXTERNAL_STORAGE,
							REQUEST_READ_EXTERNAL_STORAGE);
				} else {
					showPhotosPopup();
				}
				return;
			}
			String[] paths = ThemesRecyclerView.THUMB_PATHS[position -
					ThemesRecyclerView.GRADIENT_DRAWABLES.length - 1];
			fragment.world.postRunnable("setImagesBackground", paths[0], paths[1], paths[2]);
		}
	}

	@OnTouch(R.id.postText)
	public boolean onPostTextTouch() {
		if (photosPopup.isShowing()) {
			hidePopup(photosPopup);
		} else {
			moveBottomToolbarUp(0);
		}
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
			moveBottomToolbarUp(0);
		}
		showPopup(photosPopup);
	}

	private void hidePhotosPopup() {
		hidePopup(photosPopup);
		if (!isKeyboardShowing) {
			moveBottomToolbarDown();
		}
	}

	private void showStickersPopup() {
		popupShadow.setVisibility(View.VISIBLE);
		if (photosPopup.isShowing()) {
			if (!isKeyboardShowing) {
				needShowPhotos = true;
				mainLayout.bottomToolbar.post(new Runnable() {
					@Override
					public void run() {
						moveBottomToolbarUp(0);
					}
				});
			} else {
				moveBottomToolbarUp(0);
			}
		}
		showPopup(stickersPopup);
	}

	private void hideStickersPopup() {
		popupShadow.setVisibility(View.GONE);
		hidePopup(stickersPopup);
		if (needShowPhotos) {
			needShowPhotos = false;
			mainLayout.bottomToolbar.post(new Runnable() {
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

	private void moveBottomToolbarUp(int plusOffset) {
		mainLayout.bottomToolbar.setY(windowHeight -
				mainLayout.bottomToolbar.getHeight() - plusOffset);
	}

	private void moveBottomToolbarDown() {
		mainLayout.bottomToolbar.setY(windowHeight - bottomToolbarHeight);
	}

	@Override
	public void onBackPressed() {
		if (stickersPopup.isShowing()) {
			hideStickersPopup();
		} else if (photosPopup.isShowing() && !isKeyboardShowing) {
			hidePhotosPopup();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_READ_EXTERNAL_STORAGE:
				if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
					showPhotosPopup();
				}
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
			case REQUEST_VK_LOGIN:
				if (resultCode == AppCompatActivity.RESULT_OK) {
					((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
							.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
				}
				break;
			default:
				break;
		}
	}
}
