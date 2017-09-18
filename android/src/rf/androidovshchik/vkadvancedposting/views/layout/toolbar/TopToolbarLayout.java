package rf.androidovshchik.vkadvancedposting.views.layout.toolbar;

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

    @BindView(R.id.actionPost)
    public View actionPost;
    @BindView(R.id.slider)
    public View slider;
    @BindView(R.id.actionHistory)
    public View actionHistory;

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
}
