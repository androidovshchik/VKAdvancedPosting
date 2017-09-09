package rf.androidovshchik.vkadvancedposting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rf.androidovshchik.vkadvancedposting.stickers.AdapterStickers;
import rf.androidovshchik.vkadvancedposting.stickers.DecorationStickers;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.TopToolbarLayout;
import timber.log.Timber;

public class ActivityMain extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

	@BindView(R.id.topToolbarContainer)
	public TopToolbarLayout topToolbar;

	@BindView(R.id.stickersContainer)
	public ViewGroup stickersContainer;
	@BindView(R.id.stickersRecyclerView)
	public RecyclerView stickersRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setupStickers();
	}

	private void setupStickers() {
		final int itemsCount;
		try {
			itemsCount = getResources().getAssets().list("stickers").length;
		} catch (IOException e) {
			Timber.e(e.getMessage());
			return;
		}
		final int deviceWidth = ViewUtil.getScreen(getApplicationContext()).x;
		final int maxGridWidth = deviceWidth - DecorationStickers.MIN_ONE_SIDE_SPACE * 2 +
				DecorationStickers.SPACE_BETWEEN_ITEMS;
		int minItemWidth = AdapterStickers.MIN_ITEM_SIZE + DecorationStickers.SPACE_BETWEEN_ITEMS;
		final int itemsPerLine = maxGridWidth / minItemWidth;
		int freeSpace = maxGridWidth - itemsPerLine * minItemWidth;
		while (freeSpace >= itemsPerLine) {
			minItemWidth++;
			freeSpace -= itemsPerLine;
		}
		int leftPadding = DecorationStickers.MIN_ONE_SIDE_SPACE + freeSpace / 2;
		int rightPadding = DecorationStickers.MIN_ONE_SIDE_SPACE -
				DecorationStickers.SPACE_BETWEEN_ITEMS + freeSpace / 2 + (freeSpace > 0 &&
				freeSpace % 2 != 0 ? 1 : 0);
		stickersRecyclerView.setPadding(leftPadding, 0, rightPadding, 0);
		AdapterStickers adapterStickers = new AdapterStickers(itemsCount, minItemWidth -
				DecorationStickers.SPACE_BETWEEN_ITEMS);
		stickersRecyclerView.setAdapter(adapterStickers);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),
				itemsPerLine);
		stickersRecyclerView.setLayoutManager(gridLayoutManager);
		stickersRecyclerView.addItemDecoration(new DecorationStickers(itemsCount, itemsPerLine));
		stickersRecyclerView.setHasFixedSize(true);
		stickersRecyclerView.setItemViewCacheSize(itemsCount);
		stickersRecyclerView.setDrawingCacheEnabled(true);
		stickersRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
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
	public void onFont() {}

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
        startActivity(new Intent(getApplicationContext(), ActivityWallPost.class));
    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		topToolbar.startSlideAnimation(true);
	}

	@Override
	public void exit() {}
}
