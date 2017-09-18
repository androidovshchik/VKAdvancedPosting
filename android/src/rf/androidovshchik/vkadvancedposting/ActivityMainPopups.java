package rf.androidovshchik.vkadvancedposting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import rf.androidovshchik.vkadvancedposting.events.clicks.PhotoClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.StickerClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.ThemeClickEvent;
import rf.androidovshchik.vkadvancedposting.events.text.KeyBackEvent;
import rf.androidovshchik.vkadvancedposting.events.text.TextTouchEvent;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.photos.PhotosRecyclerView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.themes.ThemesRecyclerView;

public class ActivityMainPopups extends ActivityMainLayouts {

	@BindView(R.id.popupShadow)
	View popupShadow;

	protected PopupWindow photosPopup;
	protected PopupWindow stickersPopup;

	protected int windowHeight;
	private int bottomToolbarHeight;
	private int keyboardHeight = 0;

	private boolean isKeyboardShowing = false;
	// when keyboard is shown photosPopup doesn't disappear
	private boolean needShowPhotos = false;

	@Override
	@SuppressLint("InflateParams")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stickersPopup = ViewUtil.createPopup(getLayoutInflater().inflate(R.layout.popup_stickers,
				null, false), R.style.AnimationStickersAppear);
		stickersPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
			photosPopup = ViewUtil.createPopup(new PhotosRecyclerView(this),
					R.style.AnimationPhotosAppear);
		}
		windowHeight = ViewUtil.getWindow(getApplicationContext()).y;
		bottomToolbarHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_bottom_height);
	}

	@Override
	public void onGlobalLayout() {
		super.onGlobalLayout();
		int difference = windowHeight - rectResizedWindow.bottom;
		if (difference != 0) {
			isKeyboardShowing = true;
			keyboardHeight = difference;
			photosPopup.setHeight(keyboardHeight);
			boolean firstTime = mainLayout.bottomToolbar.getLayoutParams().height ==
					bottomToolbarHeight;
			if (mainLayout.bottomToolbar.getY() > rectResizedWindow.bottom) {
				if (firstTime) {
					mainLayout.bottomToolbar.getLayoutParams().height =
							keyboardHeight + bottomToolbarHeight;
				}
				moveBottomToolbarUp(keyboardHeight);
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

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onStickerClickEvent(StickerClickEvent event) {
		hideStickersPopup();
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

	@SuppressWarnings("all")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onThemeClickEvent(ThemeClickEvent event) {
		int position = event.position;
		mainLayout.bottomToolbar.themesRecyclerView
				.adapter.currentTheme = position;
		mainLayout.bottomToolbar.themesRecyclerView
				.adapter.notifyDataSetChanged();
		if (position <= 0) {
			String white = "ffffff";
			fragmentWorld.world.setGradientBackground(white, white);
		} else if (position < 1 + ThemesRecyclerView.GRADIENT_DRAWABLES.length) {
			String topLeftColor = ThemesRecyclerView.GRADIENT_HEXES[position - 1][0];
			String bottomRightColor = ThemesRecyclerView.GRADIENT_HEXES[position - 1][1];
			fragmentWorld.world.setGradientBackground(topLeftColor, bottomRightColor);
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
			fragmentWorld.world.postRunnable("setImagesBackground", paths[0], paths[1], paths[2]);
		}
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onTextTouchEvent(TextTouchEvent event) {
		if (photosPopup.isShowing()) {
			ViewUtil.hidePopup(photosPopup);
		} else {
			moveBottomToolbarUp(0);
		}
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onKeyBackEvent(KeyBackEvent event) {
		onBackPressed();
	}

	private void showPhotosPopup() {
		if (!isKeyboardShowing) {
			moveBottomToolbarUp(0);
		}
		ViewUtil.showPopup(photosPopup, mainLayout);
	}

	private void hidePhotosPopup() {
		ViewUtil.hidePopup(photosPopup);
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
		ViewUtil.showPopup(stickersPopup, mainLayout);
	}

	private void hideStickersPopup() {
		popupShadow.setVisibility(View.GONE);
		ViewUtil.hidePopup(stickersPopup);
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
		} else if (photosPopup.isShowing()) {
			hidePhotosPopup();
		} else if (isKeyboardShowing) {
			((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
					.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
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
					photosPopup = ViewUtil.createPopup(new PhotosRecyclerView(this),
							R.style.AnimationPhotosAppear);
					showPhotosPopup();
				}
				break;
		}
	}
}
