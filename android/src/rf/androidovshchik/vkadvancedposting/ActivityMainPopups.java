package rf.androidovshchik.vkadvancedposting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.badlogic.gdx.utils.StreamUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rf.androidovshchik.vkadvancedposting.events.clicks.PhotoClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.StickerClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.ThemeClickEvent;
import rf.androidovshchik.vkadvancedposting.events.text.KeyBackEvent;
import rf.androidovshchik.vkadvancedposting.events.text.TextTouchEvent;
import rf.androidovshchik.vkadvancedposting.utils.CameraUtil;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.layout.PhotosLayout;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.themes.ThemesRecyclerView;
import timber.log.Timber;

public class ActivityMainPopups extends ActivityMainLayouts {

	@BindView(R.id.popupShadow)
	View popupShadow;

	private PopupWindow photosPopup;
	private PopupWindow stickersPopup;

	private int windowHeight;
	private int bottomToolbarActionHeight;

	private boolean isKeyboardShowing = false;

	@Override
	@SuppressLint("InflateParams")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stickersPopup = ViewUtil.createPopup(getLayoutInflater().inflate(R.layout.popup_stickers,
				null, false), R.style.AnimationStickersAppear);
		stickersPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			photosPopup = ViewUtil.createPopup(getLayoutInflater().inflate(R.layout.popup_photos,
					null, false), R.style.AnimationPhotosAppear);
		}
		windowHeight = ViewUtil.getWindow(getApplicationContext()).y;
		bottomToolbarActionHeight =
				getResources().getDimensionPixelSize(R.dimen.toolbar_bottom_height);
	}

	@Override
	public void onGlobalLayout() {
		super.onGlobalLayout();
		int keyboardHeight = windowHeight - rectResizedWindow.bottom;
		if (keyboardHeight != 0) {
			isKeyboardShowing = true;
			if (photosPopup != null) {
				photosPopup.setHeight(keyboardHeight);
			}
			mainLayout.bottomToolbar.getLayoutParams().height =
					keyboardHeight + bottomToolbarActionHeight;
			moveBottomToolbar();
		} else {
			isKeyboardShowing = false;
			ViewUtil.hidePopup(photosPopup);
			moveBottomToolbar();
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

	@OnClick(R.id.actionSend)
	public void onSend() {
		if (stickersPopup.isShowing()) {
			hideStickersPopup();
		} else if (photosPopup.isShowing()) {
			ViewUtil.hidePopup(photosPopup);
		}
		if (isKeyboardShowing) {
			ViewUtil.hideKeyboard(getApplicationContext());
		}
		final Bitmap postText = ViewUtil.getBitmapFromView(fragmentPostText.getPostEditText());
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				final Pixmap pixmap = fragmentWorld.world.getScreenshot();
				Observable.fromCallable(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						PNG writer = new PNG((int)(pixmap.getWidth() * pixmap.getHeight() * 1.5f));
						writer.setFlipY(false);
						ByteArrayOutputStream output = new ByteArrayOutputStream();
						try {
							writer.write(output, pixmap);
						} finally {
							StreamUtils.closeQuietly(output);
							writer.dispose();
							pixmap.dispose();
						}
						byte[] bytes = output.toByteArray();
						uploadImage(ViewUtil.overlayBitmapToCenter(BitmapFactory
								.decodeByteArray(bytes, 0, bytes.length), postText));
						return true;
					}
				}).subscribeOn(Schedulers.io())
						.subscribe(new Consumer<Boolean>() {
							@Override
							public void accept(Boolean success) {

							}
						});
			}
		});
		dialogWallPost.show(getSupportFragmentManager(), DialogWallPost.class.getSimpleName());
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
        if (event.position <= 0) {
			if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				requirePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
						REQUEST_WRITE_GALLERY);
			} else {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_GALLERY_GET_IMAGE);
			}
        } else if (event.position == 1) {
			if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				requirePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
						REQUEST_WRITE_CAMERA);
			} else {
				Intent intent = CameraUtil.getCameraIntent(getApplicationContext(), null);
				uri = intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
				startActivityForResult(intent, REQUEST_CAMERA_GET_IMAGE);
			}
        } else {
            String sdcardPath = Environment.getExternalStorageDirectory().getPath() + "/";
            String path = ((PhotosLayout) photosPopup.getContentView())
                    .photosRecyclerView.adapterPhotos.photoPaths.get(event.position - 2).getPath();
			int orientation = CameraUtil.getOrientation(path);
            fragmentWorld.world.postRunnable("setPhotoBackground",
					path.substring(sdcardPath.length()), orientation);
        }
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
				if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					requirePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
							REQUEST_WRITE_POPUP);
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
		}
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onKeyBackEvent(KeyBackEvent event) {
		onBackPressed();
	}

	private void showPhotosPopup() {
		if (!isKeyboardShowing) {
			ViewUtil.showKeyboard(getApplicationContext());
		}
		mainLayout.bottomToolbar.post(new Runnable() {
			@Override
			public void run() {
				ViewUtil.showPopup(photosPopup, mainLayout);
			}
		});
	}

	private void hidePhotosPopup() {
		ViewUtil.hideKeyboard(getApplicationContext());
	}

	private void showStickersPopup() {
		popupShadow.setVisibility(View.VISIBLE);
		if (photosPopup.isShowing()) {
			((PhotosLayout) photosPopup.getContentView()).photosShadow.setVisibility(View.VISIBLE);
		}
		ViewUtil.showPopup(stickersPopup, mainLayout);
	}

	private void hideStickersPopup() {
		popupShadow.setVisibility(View.GONE);
		if (photosPopup.isShowing()) {
			((PhotosLayout) photosPopup.getContentView()).photosShadow.setVisibility(View.GONE);
		}
		ViewUtil.hidePopup(stickersPopup);
	}

	private void moveBottomToolbar() {
		mainLayout.bottomToolbar.setY(rectResizedWindow.bottom - bottomToolbarActionHeight);
	}

	@Override
	public void onBackPressed() {
		if (stickersPopup.isShowing()) {
			hideStickersPopup();
		} else if (photosPopup.isShowing()) {
			hidePhotosPopup();
		} else if (isKeyboardShowing) {
			ViewUtil.hideKeyboard(getApplicationContext());
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor != null && cursor.moveToFirst()) {
			String sdcardPath = Environment.getExternalStorageDirectory().getPath() + "/";
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			if (path == null) {
				return;
			}
			Timber.d("path " + path);
			int orientation = CameraUtil.getOrientation(path);
			Timber.d("orientation " + orientation);
			fragmentWorld.world.postRunnable("setPhotoBackground",
					path.substring(sdcardPath.length()), orientation);
		}
	}

	@Override
	@SuppressLint("InflateParams")
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_WRITE_POPUP:
				if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					photosPopup = ViewUtil.createPopup(getLayoutInflater()
							.inflate(R.layout.popup_photos,
									null, false), R.style.AnimationPhotosAppear);
					showPhotosPopup();
				}
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != REQUEST_CAMERA_GET_IMAGE && requestCode != REQUEST_GALLERY_GET_IMAGE) {
			return;
		}
		ContentResolver contentResolver = getContentResolver();
		if (resultCode != AppCompatActivity.RESULT_OK) {
			// if failed/canceled to take/select photo
			if (uri != null) {
				try {
					contentResolver.delete(uri, null, null);
				} catch (Exception e) {
					Timber.e(e.getMessage());
					// there is no photo
				}
				uri = null;
			}
			return;
		}
		Uri resultUri = null;
		switch (requestCode) {
			case REQUEST_CAMERA_GET_IMAGE:
				// success taking photo
				if (data != null && data.getData() != null) {
					resultUri = data.getData();
				} else {
					resultUri = uri;
				}
				break;
			case REQUEST_GALLERY_GET_IMAGE:
				// success selecting photo
				if (data != null && data.getData() != null) {
					resultUri = data.getData();
				}
				break;
			default:
				return;
		}
		if (resultUri != null) {
			// resultUri is URI of photo
			uri = resultUri;
			LoaderManager loaderManager = getSupportLoaderManager();
			if (loaderManager != null) {
				if (loaderManager.getLoader(CURSOR_PICKER) != null) {
					loaderManager.restartLoader(CURSOR_PICKER, null, this);
				} else {
					loaderManager.initLoader(CURSOR_PICKER, null, this);
				}
			}
		}
	}
}