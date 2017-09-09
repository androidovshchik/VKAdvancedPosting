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
    private static final float MIN_LAYOUT_OPACITY = 0.92f;
    private static final float MIN_TEXT_OPACITY = 0.72f;

    @BindView(R.id.background)
    protected View background;
    @BindView(R.id.actionPost)
    protected View actionPost;
    @BindView(R.id.slider)
    protected View slider;
    @BindView(R.id.actionHistory)
    protected View actionHistory;

    private Unbinder unbinder;

    private boolean isActivePost = true;

    private Float sliderStartX = null;
    private Float sliderMaxDistance = null;
    private Float sliderScaleFactor = null;
    private Float sliderHistoryScale = null;
    private static final float LAYOUT_ALPHA_FACTOR = MIN_LAYOUT_OPACITY - 1;
    private static final float POST_ALPHA_FACTOR = MIN_TEXT_OPACITY - 1;
    private static final float HISTORY_ALPHA_FACTOR = 1 - MIN_TEXT_OPACITY;

    private AnimatorSet animatorSet;
    private ObjectAnimator sliderTranslationX;
    private ObjectAnimator sliderScaleX;
    private ObjectAnimator alphaBackground;
    private ObjectAnimator alphaPost;
    private ObjectAnimator alphaHistory;

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
        sliderTranslationX = ObjectAnimator.ofFloat(slider, "translationX", 0f);
        sliderTranslationX.setRepeatCount(Animation.ABSOLUTE);
        sliderScaleX = ObjectAnimator.ofFloat(slider, "scaleX", 0f);
        sliderScaleX.setRepeatCount(Animation.ABSOLUTE);
        alphaBackground = ObjectAnimator.ofFloat(background, "alpha", 0f);
        alphaBackground.setRepeatCount(Animation.ABSOLUTE);
        alphaPost = ObjectAnimator.ofFloat(actionPost, "alpha", 0f);
        alphaPost.setRepeatCount(Animation.ABSOLUTE);
        alphaHistory = ObjectAnimator.ofFloat(actionHistory, "alpha", 0f);
        alphaHistory.setRepeatCount(Animation.ABSOLUTE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(sliderTranslationX, sliderScaleX, alphaBackground, alphaPost,
                alphaHistory);
        slider.setPivotX(0f);
    }

    public void onPostClicked(boolean immediately) {
        if (!isActivePost) {
            isActivePost = true;
            startSlideAnimation(immediately);
        }
    }

    public void onHistoryClicked(boolean immediately) {
        if (isActivePost) {
            isActivePost = false;
            startSlideAnimation(immediately);
        }
    }

    public void startSlideAnimation(boolean immediately) {
        initVars();
        float currentDistance = getDistance(slider.getX());
        if (isActivePost) {
            sliderTranslationX.setFloatValues(currentDistance, 0f);
            sliderScaleX.setFloatValues(getRatio(sliderScaleFactor, currentDistance), 1f);
            alphaBackground.setFloatValues(getRatio(LAYOUT_ALPHA_FACTOR, currentDistance), 1f);
            alphaPost.setFloatValues(getRatio(POST_ALPHA_FACTOR, currentDistance), 1f);
            alphaHistory.setFloatValues(getRatio(HISTORY_ALPHA_FACTOR, currentDistance) - 1 +
                            MIN_TEXT_OPACITY, MIN_TEXT_OPACITY);
            animatorSet.setDuration(Math.round(ANIMATION_MAX_TIME *
                    currentDistance / sliderMaxDistance));
        } else {
            sliderTranslationX.setFloatValues(currentDistance, sliderMaxDistance);
            sliderScaleX.setFloatValues(getRatio(sliderScaleFactor, currentDistance),
                    sliderHistoryScale);
            alphaBackground.setFloatValues(getRatio(LAYOUT_ALPHA_FACTOR, currentDistance),
                    MIN_LAYOUT_OPACITY);
            alphaPost.setFloatValues(getRatio(POST_ALPHA_FACTOR, currentDistance), MIN_TEXT_OPACITY);
            alphaHistory.setFloatValues(getRatio(HISTORY_ALPHA_FACTOR, currentDistance) - 1 +
                    MIN_TEXT_OPACITY, 1f);
            animatorSet.setDuration(Math.round(ANIMATION_MAX_TIME * (sliderMaxDistance -
                    currentDistance) / sliderMaxDistance));
        }
        if (immediately) {
            animatorSet.setDuration(0);
        }
        animatorSet.start();
    }

    private float getDistance(float x) {
        return x - sliderStartX;
    }

    private void initVars() {
        if (sliderStartX == null) {
            sliderStartX = slider.getX();
        }
        if (sliderScaleFactor == null) {
            sliderScaleFactor = (float) actionHistory.getWidth() / actionPost.getWidth() - 1f;
        }
        if (sliderMaxDistance == null) {
            sliderMaxDistance = (float) actionPost.getWidth();
        }
        if (sliderHistoryScale == null) {
            sliderHistoryScale = getRatio(sliderScaleFactor, sliderMaxDistance);
        }
    }

    private float getRatio(float factor, float x) {
        return 1 + factor * x / sliderMaxDistance;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
