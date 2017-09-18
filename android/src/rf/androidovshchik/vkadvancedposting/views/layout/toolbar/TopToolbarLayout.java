package rf.androidovshchik.vkadvancedposting.views.layout.toolbar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import butterknife.BindView;
import rf.androidovshchik.vkadvancedposting.R;

public class TopToolbarLayout extends ToolbarLayout {

    public static final int ANIMATION_MAX_TIME = 300;
    public static final float MIN_TEXT_OPACITY = 0.72f;

    @BindView(R.id.actionPost)
    public View actionPost;
    @BindView(R.id.slider)
    public View slider;
    @BindView(R.id.actionHistory)
    public View actionHistory;

    private static final float LAYOUT_ALPHA_FACTOR = MIN_BACKGROUND_OPACITY - 1;
    private static final float POST_ALPHA_FACTOR = MIN_TEXT_OPACITY - 1;
    private static final float HISTORY_ALPHA_FACTOR = 1 - MIN_TEXT_OPACITY;
    private Float sliderStartX = null;
    private Float sliderMaxDistance = null;
    private Float sliderScaleFactor = null;
    private Float sliderHistoryScale = null;

    public ObjectAnimator sliderTranslationX;
    public ObjectAnimator sliderScaleX;
    public ObjectAnimator alphaPost;
    public ObjectAnimator alphaHistory;

    public TopToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public TopToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        sliderTranslationX = ObjectAnimator.ofFloat(slider, "translationX", 0f);
        sliderTranslationX.setRepeatCount(Animation.ABSOLUTE);
        sliderScaleX = ObjectAnimator.ofFloat(slider, "scaleX", 0f);
        sliderScaleX.setRepeatCount(Animation.ABSOLUTE);
        alphaPost = ObjectAnimator.ofFloat(actionPost, "alpha", 0f);
        alphaPost.setRepeatCount(Animation.ABSOLUTE);
        alphaHistory = ObjectAnimator.ofFloat(actionHistory, "alpha", 0f);
        alphaHistory.setRepeatCount(Animation.ABSOLUTE);
        slider.setPivotX(0f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        sliderStartX = slider.getX();
        sliderScaleFactor = (float) actionHistory.getWidth() / actionPost.getWidth() - 1f;
        sliderMaxDistance = (float) actionPost.getWidth();
        sliderHistoryScale = getRatio(sliderScaleFactor, sliderMaxDistance);
    }

    public void startSlideAnimation(boolean immediately, boolean isPostMode,
                                    ObjectAnimator alphaBottomBackground, AnimatorSet animatorSet) {
        float currentDistance = getDistance(slider.getX());
        if (isPostMode) {
            sliderTranslationX.setFloatValues(currentDistance, 0f);
            sliderScaleX.setFloatValues(getRatio(sliderScaleFactor, currentDistance), 1f);
            alphaBackground.setFloatValues(getRatio(LAYOUT_ALPHA_FACTOR, currentDistance), 1f);
            alphaPost.setFloatValues(getRatio(POST_ALPHA_FACTOR, currentDistance), 1f);
            alphaHistory.setFloatValues(getRatio(HISTORY_ALPHA_FACTOR, currentDistance) - 1 +
                    MIN_TEXT_OPACITY, MIN_TEXT_OPACITY);
            animatorSet.setDuration(immediately ? 0 : Math.round(ANIMATION_MAX_TIME *
                    currentDistance / sliderMaxDistance));
        } else {
            sliderTranslationX.setFloatValues(currentDistance, sliderMaxDistance);
            sliderScaleX.setFloatValues(getRatio(sliderScaleFactor, currentDistance),
                    sliderHistoryScale);
            alphaBackground.setFloatValues(getRatio(LAYOUT_ALPHA_FACTOR, currentDistance),
                    MIN_BACKGROUND_OPACITY);
            alphaPost.setFloatValues(getRatio(POST_ALPHA_FACTOR, currentDistance), MIN_TEXT_OPACITY);
            alphaHistory.setFloatValues(getRatio(HISTORY_ALPHA_FACTOR, currentDistance) - 1 +
                    MIN_TEXT_OPACITY, 1f);
            animatorSet.setDuration(immediately ? 0 : Math.round(ANIMATION_MAX_TIME *
                    (sliderMaxDistance - currentDistance) / sliderMaxDistance));
        }
        animatorSet.start();
    }

    private float getDistance(float x) {
        return x - sliderStartX;
    }

    private float getRatio(float factor, float x) {
        return 1 + factor * x / sliderMaxDistance;
    }
}
