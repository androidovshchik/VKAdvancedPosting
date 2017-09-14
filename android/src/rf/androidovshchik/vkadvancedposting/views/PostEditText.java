package rf.androidovshchik.vkadvancedposting.views;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class PostEditText extends AppCompatButton {

    private static final int ANIMATION_TIME_APPEAR = 300;
    private static final int ANIMATION_DELAY_APPEAR = 500;
    private static final int ANIMATION_TIME_FADE = 200;

    public PostEditText(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public PostEditText(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAllCaps(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }
    }

    public ObjectAnimator createAnimationAppear(Interpolator interpolator) {
        int marginBottom = ((ViewGroup.MarginLayoutParams) getLayoutParams()).bottomMargin;
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this,
                PropertyValuesHolder.ofFloat(TRANSLATION_Y, marginBottom + ViewUtil.dp2px(4), 0),
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1));
        return setupAnimation(objectAnimator, interpolator, ANIMATION_DELAY_APPEAR,
                ANIMATION_TIME_APPEAR);
    }

    public ObjectAnimator createAnimationFadeIn(Interpolator interpolator) {
        return createAnimationFade(interpolator, 0, 1);
    }

    public ObjectAnimator createAnimationFadeOut(Interpolator interpolator) {
        return createAnimationFade(interpolator, 1, 0);
    }

    private ObjectAnimator createAnimationFade(Interpolator interpolator, float from, float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", from, to);
        return setupAnimation(objectAnimator, interpolator, 0, ANIMATION_TIME_FADE);
    }

    private ObjectAnimator setupAnimation(ObjectAnimator objectAnimator, Interpolator interpolator,
                                           int startDelay, int duration) {
        objectAnimator.setRepeatCount(Animation.ABSOLUTE);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.setStartDelay(startDelay);
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }
}
