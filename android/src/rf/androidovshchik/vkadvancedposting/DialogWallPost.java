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

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DialogWallPost extends DialogFragment {

    public static final String EXTRA_POST_PUBLISHED = "postPublished";

    private Unbinder unbinder;

    private boolean isFirstStart = false;
    private boolean isPostPublished = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_WallPost);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFirstStart = savedInstanceState == null;
        if (savedInstanceState != null) {
            isPostPublished = savedInstanceState.getBoolean(EXTRA_POST_PUBLISHED, false);
        }
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
        if (isFirstStart && window != null) {
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
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.actionRepeat)
    protected void onRepeat() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_POST_PUBLISHED, isPostPublished);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}