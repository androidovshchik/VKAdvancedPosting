package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

	@BindView(R.id.stickersContainer)
	ViewGroup stickersContainer;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.actionSticker)
	public void onStickers() {
		BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(stickersContainer);
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
	}

	@Override
	public void exit() {}
}
