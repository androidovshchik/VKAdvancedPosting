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
import rf.androidovshchik.vkadvancedposting.views.layout.WallPostLayout;

public class DialogWallPost extends DialogFragment {

    public static final String EXTRA_IS_FIRST_START = "wallPostIsFirstStart";
    public static final String EXTRA_IS_PUBLISHING = "wallPostIsPublishing";
    public static final String EXTRA_HAS_POST_PUBLISHED = "wallPostHasPostPublished";

    @BindView(R.id.wallPostContainer)
    protected WallPostLayout wallPostLayout;

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
        if (savedInstanceState != null) {
            wallPostLayout.isFirstStart = savedInstanceState.getBoolean(EXTRA_IS_FIRST_START, true);
            wallPostLayout.isPublishing = savedInstanceState.getBoolean(EXTRA_IS_PUBLISHING, true);
            wallPostLayout.hasPostPublished =
                    savedInstanceState.getBoolean(EXTRA_HAS_POST_PUBLISHED, false);
        }
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
        if (wallPostLayout.isFirstStart && window != null) {
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
        dismissAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_IS_FIRST_START, false);
        outState.putBoolean(EXTRA_IS_PUBLISHING, wallPostLayout.isPublishing);
        outState.putBoolean(EXTRA_HAS_POST_PUBLISHED, wallPostLayout.hasPostPublished);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}