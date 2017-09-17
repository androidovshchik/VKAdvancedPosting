package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;

public abstract class ActivityMainWorld extends ActivityMainBase {

	protected FragmentWorld fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragment = new FragmentWorld();
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.world, fragment, FragmentWorld.class.getSimpleName())
				.commitAllowingStateLoss();
	}
}
