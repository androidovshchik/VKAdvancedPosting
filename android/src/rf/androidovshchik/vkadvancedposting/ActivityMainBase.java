package rf.androidovshchik.vkadvancedposting;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.vk.sdk.VKSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import rf.androidovshchik.vkadvancedposting.events.VKInvalidTokenEvent;

public abstract class ActivityMainBase extends AppCompatActivity
		implements AndroidFragmentApplication.Callbacks, ViewTreeObserver.OnGlobalLayoutListener {

	protected static final int REQUEST_VK_LOGIN = 1;
	protected static final int REQUEST_PROMPT_SETTINGS = 2;

	protected static final int REQUEST_READ_EXTERNAL_STORAGE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		if (!VKSdk.isLoggedIn()) {
			startActivityForResult(new Intent(getApplicationContext(), ActivityLogin.class),
					REQUEST_VK_LOGIN);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
		getWindow().getDecorView().getViewTreeObserver()
				.addOnGlobalLayoutListener(this);
	}

	@SuppressWarnings("unused")
	@Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
	public void onVKInvalidTokenEvent(VKInvalidTokenEvent event) {

	}

	protected boolean hasPermission(String permission) {
		return checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
	}

	protected void requirePermission(String permission, int request) {
		ActivityCompat.requestPermissions(this, new String[] { permission }, request);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		for (String permission : new String[] {
				Manifest.permission.READ_EXTERNAL_STORAGE }) {
			if (!hasPermission(permission) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
					!shouldShowRequestPermissionRationale(permission)) {
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.setNegativeButton(getString(android.R.string.cancel), null)
						.create();
				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(android.R.string.ok), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int which) {
								Intent intent =
										new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
								intent.setData(Uri.fromParts("package", getPackageName(), null));
								startActivityForResult(intent, REQUEST_PROMPT_SETTINGS);
							}
						});
				alertDialog.setMessage(getString(R.string.prompt_settings));
				alertDialog.show();
				return;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
			case REQUEST_VK_LOGIN:
				if (resultCode != AppCompatActivity.RESULT_OK) {
					finish();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void exit() {}

	@Override
	public void onStop() {
		super.onStop();
		getWindow().getDecorView().getViewTreeObserver()
				.removeOnGlobalLayoutListener(this);
		EventBus.getDefault().unregister(this);
	}
}
