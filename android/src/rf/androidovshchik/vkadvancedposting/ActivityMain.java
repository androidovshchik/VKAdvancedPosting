package rf.androidovshchik.vkadvancedposting;

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
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import timber.log.Timber;

public class ActivityMain extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

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
		AdapterStickers adapterStickers = new AdapterStickers(itemsCount);
		stickersRecyclerView.setAdapter(adapterStickers);
		stickersRecyclerView.setItemViewCacheSize(itemsCount);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),
				Math.round(ViewUtil.getScreen(getApplicationContext()).x / AdapterStickers.ITEM_SIZE));
		stickersRecyclerView.setLayoutManager(gridLayoutManager);
		stickersRecyclerView.setDrawingCacheEnabled(true);
		stickersRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		stickersRecyclerView.setHasFixedSize(true);
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

	@Override
	public void exit() {}
}
