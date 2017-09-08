package rf.androidovshchik.vkadvancedposting.views;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;

public class ToolbarLayout extends RelativeLayout {

    @BindView(R.id.slider)
    protected View slider;
    @BindView(R.id.actionPost)
    protected View actionPost;
    @BindView(R.id.actionHistory)
    protected View actionHistory;

    private Unbinder unbinder;

    private boolean isActionPost = true;

    private ObjectAnimator translationX;

    public ToolbarLayout(Context context) {
        super(context);
    }

    public ToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        translationX = ObjectAnimator.ofFloat(slider, "translationX", 0f);
        translationX.setDuration(300);
        translationX.setRepeatCount(Animation.ABSOLUTE);
    }

    public void onPostClicked() {
        if (!isActionPost) {
            isActionPost = true;
            startSlideAnimation();
        }
    }

    public void onHistoryClicked() {
        if (isActionPost) {
            isActionPost = false;
            startSlideAnimation();
        }
    }

    private void startSlideAnimation() {
        if (isActionPost) {
            translationX.setFloatValues(slider.getWidth(), 0f);
        } else {
            translationX.setFloatValues(0f, slider.getWidth());
        }
        translationX.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
