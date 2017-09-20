package rf.androidovshchik.vkadvancedposting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.StreamUtils;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import rf.androidovshchik.vkadvancedposting.events.remote.VKRepeatEvent;
import rf.androidovshchik.vkadvancedposting.events.world.StickerPressEvent;
import rf.androidovshchik.vkadvancedposting.events.remote.sticky.VKResponseEvent;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
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

	@OnClick(R.id.actionFont)
	public void onFont() {
		switch (fragmentPostText.getPostEditText().backgroundColor) {
			case Color.TRANSPARENT:
				if (fragmentPostText.getPostEditText().getCurrentTextColor() != Color.WHITE) {
					fragmentPostText.getPostEditText().backgroundColor = Color.WHITE;
					fragmentPostText.getPostEditText().invalidate();
					break;
				}
			case Color.WHITE:
				fragmentPostText.getPostEditText().backgroundColor = Color.parseColor("#1e000000");
				fragmentPostText.getPostEditText().invalidate();
				break;
			default:
				fragmentPostText.getPostEditText().backgroundColor = Color.TRANSPARENT;
				fragmentPostText.getPostEditText().invalidate();
				break;
		}
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
	public void onStickerPressEvent(StickerPressEvent event) {
		mainLayout.trashFab.stickerPressed = true;
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

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onVKRepeatEvent(VKRepeatEvent event) {
		fragmentPostText.getPostEditText().setCursorVisible(false);
		final Bitmap postText = ViewUtil.getBitmapFromView(fragmentPostText.getPostEditText());
		fragmentPostText.getPostEditText().setCursorVisible(true);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				final Pixmap pixmap = fragmentWorld.world.getScreenshot();
				Observable.fromCallable(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						PixmapIO.PNG writer = new PixmapIO.PNG((int)(pixmap.getWidth() *
								pixmap.getHeight() * 1.5f));
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
						.subscribe();
			}
		});
	}
}
