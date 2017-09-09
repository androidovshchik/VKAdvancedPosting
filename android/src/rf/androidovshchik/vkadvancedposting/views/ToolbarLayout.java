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

    private static final int ANIMATION_MAX_TIME = 3000;
    private static final float MIN_LAYOUT_OPACITY = 0.92f;
    private static final float MIN_TEXT_OPACITY = 0.72f;

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
    private Float sliderScaleFactor = null;
    private Float sliderHistoryScale = null;
    private Float layoutAlphaFactor = null;
    private Float postAlphaFactor = null;
    private Float historyAlphaFactor = null;

    private AnimatorSet animatorSet;
    private ObjectAnimator sliderTranslationX;
    private ObjectAnimator sliderScaleX;
    private ObjectAnimator alphaLayout;
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
        alphaLayout = ObjectAnimator.ofFloat(this, "alpha", 0f);
        alphaLayout.setRepeatCount(Animation.ABSOLUTE);
        alphaPost = ObjectAnimator.ofFloat(actionPost, "alpha", 0f);
        alphaPost.setRepeatCount(Animation.ABSOLUTE);
        alphaHistory = ObjectAnimator.ofFloat(actionHistory, "alpha", 0f);
        alphaHistory.setRepeatCount(Animation.ABSOLUTE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(sliderTranslationX, sliderScaleX, alphaLayout, alphaPost, alphaHistory);
        slider.setPivotX(0f);
    }

    public void onPostClicked() {
        if (!isActivePost) {
            isActivePost = true;
            startSlideAnimation(slider.getX());
        }
    }

    public void onHistoryClicked() {
        if (isActivePost) {
            isActivePost = false;
            startSlideAnimation(slider.getX());
        }
    }

    public void startSlideAnimation(float x) {
        initVars();
        float currentDistance = getDistance(x);
        if (isActivePost) {
            sliderTranslationX.setFloatValues(currentDistance, 0f);
            sliderScaleX.setFloatValues(getRatio(sliderScaleFactor, currentDistance), 1f);
            alphaLayout.setFloatValues(getRatio(layoutAlphaFactor, currentDistance), 1f);
            alphaPost.setFloatValues(getRatio(postAlphaFactor, currentDistance), 1f);
            alphaHistory.setFloatValues(getRatio(historyAlphaFactor, currentDistance), MIN_TEXT_OPACITY);
            animatorSet.setDuration(Math.round(ANIMATION_MAX_TIME *
                    currentDistance / sliderMaxDistance));
        } else {
            sliderTranslationX.setFloatValues(currentDistance, sliderMaxDistance);
            sliderScaleX.setFloatValues(getRatio(sliderScaleFactor, currentDistance), sliderHistoryScale);
            alphaLayout.setFloatValues(getRatio(layoutAlphaFactor, currentDistance), MIN_LAYOUT_OPACITY);
            alphaPost.setFloatValues(getRatio(postAlphaFactor, currentDistance), MIN_TEXT_OPACITY);
            alphaHistory.setFloatValues(getRatio(historyAlphaFactor, currentDistance), 1f);
            animatorSet.setDuration(Math.round(ANIMATION_MAX_TIME * (sliderMaxDistance -
                    currentDistance) / sliderMaxDistance));
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
        if (layoutAlphaFactor == null) {
            layoutAlphaFactor = MIN_LAYOUT_OPACITY - 1f;
        }
        if (postAlphaFactor == null) {
            postAlphaFactor = 1f - MIN_TEXT_OPACITY;
        }
        if (historyAlphaFactor == null) {
            historyAlphaFactor = MIN_TEXT_OPACITY - 1f;
        }
        if (sliderMaxDistance == null) {
            sliderMaxDistance = (float) actionPost.getWidth();
        }
        if (sliderHistoryScale == null) {
            sliderHistoryScale = getRatio(sliderScaleFactor, sliderMaxDistance);
        }
    }

    private float getRatio(float factor, float x) {
        return 1f + factor * x / sliderMaxDistance;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
