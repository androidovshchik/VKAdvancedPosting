package rf.androidovshchik.vkadvancedposting;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.events.remote.VKRepeatEvent;
import rf.androidovshchik.vkadvancedposting.events.world.RemoveAllEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;
import rf.androidovshchik.vkadvancedposting.views.layout.WallPostLayout;

public class DialogWallPost extends DialogFragment {

    @BindView(R.id.wallPostLayout)
    public WallPostLayout wallPostLayout;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_WallPost);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_wall_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            ObjectAnimator appearing = ObjectAnimator.ofFloat(window.getDecorView(),
                    "alpha", 0f, 1f);
            appearing.setRepeatCount(Animation.ABSOLUTE);
            appearing.setInterpolator(new AccelerateInterpolator());
            appearing.setDuration(300);
            appearing.start();
        }
    }

    @OnClick(R.id.actionCancel)
    protected void onCancel() {
        wallPostLayout.onPublishCancel();
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.actionBackwards)
    protected void onBackwards() {
        if (wallPostLayout.backwardsCreateNew) {
            EventUtil.post(new RemoveAllEvent());
            dismissAllowingStateLoss();
        } else if (wallPostLayout.backwardsRetry) {
            EventUtil.post(new VKRepeatEvent());
            wallPostLayout.onPublishRetry();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}