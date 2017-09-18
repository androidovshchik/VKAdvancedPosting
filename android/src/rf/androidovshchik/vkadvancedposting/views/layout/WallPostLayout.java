package rf.androidovshchik.vkadvancedposting.views.layout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.views.ProgressBar;
import rf.androidovshchik.vkadvancedposting.views.VKButton;
import timber.log.Timber;

public class WallPostLayout extends RelativeLayout implements Animator.AnimatorListener {

    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;
    @BindView(R.id.loaderImage)
    protected ImageView loaderImage;
    @BindView(R.id.loaderText)
    protected TextView loaderText;
    @BindView(R.id.actionCancel)
    protected VKButton actionCancel;
    @BindView(R.id.actionBackwards)
    protected VKButton actionBackwards;

    public boolean isFirstStart = true;
    public boolean isPublishing = true;
    public boolean hasPostPublished = false;

    private AccelerateInterpolator accelerateInterpolator;
    @Nullable
    private ObjectAnimator cancelAppearing;
    private ObjectAnimator cancelDisappearing;
    private ObjectAnimator backwardsAppearing;

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
        if (isFirstStart) {
            cancelAppearing = actionCancel.createAnimationAppear(accelerateInterpolator);
            cancelAppearing.addListener(this);
            cancelAppearing.start();
        } else if (hasPostPublished) {
            onPublishSucceed();
        } else if (!isPublishing) {
            onPublishFailed();
        } else {
            actionCancel.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onAnimationStart(Animator animator) {
        if (isPublishing) {
            actionCancel.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        animator.removeListener(this);
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        animator.removeListener(this);
    }

    @Override
    public void onAnimationRepeat(Animator animator) {}

    public void onPublishCancel() {
        if (cancelAppearing != null && cancelAppearing.isRunning()) {
            cancelAppearing.cancel();
        }
    }

    public void onPublishSucceed() {
        if (cancelAppearing != null && cancelAppearing.isRunning()) {
            cancelAppearing.end();
        }
        progressBar.overDrawProgress = true;
        loaderText.setText(R.string.main_publication_succeed);
        loaderImage.setVisibility(VISIBLE);
        loaderImage.setImageResource(R.drawable.ic_loader_success);
    }

    public void onPublishFailed() {
        if (cancelAppearing != null && cancelAppearing.isRunning()) {
            cancelAppearing.end();
        }
        progressBar.overDrawProgress = true;
        progressBar.setVisibility(INVISIBLE);
        loaderText.setText(R.string.main_publication_failed);
        loaderImage.setVisibility(VISIBLE);
        loaderImage.setImageResource(R.drawable.ic_sentiment_dissatisfied);
    }

    public void onPublishRetry() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        loaderText.setText(R.string.main_publication_progress);
        loaderImage.setVisibility(INVISIBLE);
        unbinder.unbind();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
