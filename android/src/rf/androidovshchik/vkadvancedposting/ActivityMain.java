package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class ActivityMain extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void exit() {}
}
