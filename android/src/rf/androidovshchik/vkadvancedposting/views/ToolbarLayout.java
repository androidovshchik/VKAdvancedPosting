package rf.androidovshchik.vkadvancedposting.views;

import android.animation.AnimatorSet;
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

    private static final int ANIMATION_MAX_TIME = 300;

    @BindView(R.id.slider)
    protected View slider;
    @BindView(R.id.actionPost)
    protected View actionPost;
    @BindView(R.id.actionHistory)
    protected View actionHistory;

    private Unbinder unbinder;

    private boolean isActivePost = true;

    private Float sliderStartX = null;
    private Float sliderMaxDistance = null;
    private Float sliderRatioFactor = null;
    private Float sliderHistoryRatio = null;

    private AnimatorSet sliderSet;
    private ObjectAnimator translationX;
    private ObjectAnimator scaleX;

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
        translationX.setRepeatCount(Animation.ABSOLUTE);
        scaleX = ObjectAnimator.ofFloat(slider, "scaleX", 0f);
        scaleX.setRepeatCount(Animation.ABSOLUTE);
        sliderSet = new AnimatorSet();
        sliderSet.playTogether(translationX, scaleX);
        slider.setPivotX(0f);
    }

    public void onPostClicked() {
        if (!isActivePost) {
            isActivePost = true;
            startSlideAnimation();
        }
    }

    public void onHistoryClicked() {
        if (isActivePost) {
            isActivePost = false;
            startSlideAnimation();
        }
    }

    private void startSlideAnimation() {
        initVars();
        float currentDistance = getDistance(slider.getX());
        if (isActivePost) {
            translationX.setFloatValues(currentDistance, 0f);
            scaleX.setFloatValues(getSliderRatio(currentDistance), 1f);
            sliderSet.setDuration(Math.round(ANIMATION_MAX_TIME * currentDistance / sliderMaxDistance));
        } else {
            translationX.setFloatValues(currentDistance, sliderMaxDistance);
            scaleX.setFloatValues(getSliderRatio(currentDistance), sliderHistoryRatio);
            sliderSet.setDuration(Math.round(ANIMATION_MAX_TIME * (sliderMaxDistance -
                    currentDistance) / sliderMaxDistance));
        }
        sliderSet.start();
    }

    private void initVars() {
        if (sliderStartX == null) {
            sliderStartX = slider.getX();
        }
        if (sliderRatioFactor == null) {
            sliderRatioFactor = (float) actionHistory.getWidth() / actionPost.getWidth() - 1f;
        }
        if (sliderMaxDistance == null) {
            sliderMaxDistance = getDistance(sliderStartX + actionPost.getWidth());
        }
        if (sliderHistoryRatio == null) {
            sliderHistoryRatio = getSliderRatio(sliderMaxDistance);
        }
    }

    private float getSliderRatio(float x) {
        return 1f + sliderRatioFactor * x / sliderMaxDistance;
    }

    private float getDistance(float x) {
        return x - sliderStartX;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
