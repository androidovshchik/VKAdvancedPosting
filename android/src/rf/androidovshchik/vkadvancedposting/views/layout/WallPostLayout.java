package rf.androidovshchik.vkadvancedposting.views.layout;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class WallPostLayout extends RelativeLayout {

    @BindView(R.id.progressBar)
    protected MaterialProgressBar progressBar;
    @BindView(R.id.loaderImage)
    protected ImageView loaderImage;
    @BindView(R.id.loaderText)
    protected TextView loaderText;
    @BindView(R.id.actionCancel)
    protected View actionCancel;
    @BindView(R.id.actionRepeat)
    protected View actionRepeat;

    public boolean isFirstStart = false;
    public boolean isPostPublished = false;

    private AccelerateInterpolator accelerateInterpolator;

    private Unbinder unbinder;

    public WallPostLayout(Context context) {
        super(context);
    }

    public WallPostLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WallPostLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public WallPostLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        accelerateInterpolator = new AccelerateInterpolator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        int cancelMarginBottom = ((MarginLayoutParams) actionCancel.getLayoutParams()).bottomMargin;
        ObjectAnimator appearing = ObjectAnimator.ofPropertyValuesHolder(actionCancel,
                PropertyValuesHolder.ofFloat(TRANSLATION_Y, cancelMarginBottom + ViewUtil.dp2px(4), 0),
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1));
        appearing.setRepeatCount(Animation.ABSOLUTE);
        appearing.setInterpolator(accelerateInterpolator);
        appearing.setDuration(300);
        appearing.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
