package rf.androidovshchik.vkadvancedposting.views.layout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

    public boolean backwardsCreateNew = false;
    public boolean backwardsRetry = false;

    private ObjectAnimator cancelAppearing;

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
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        cancelAppearing = actionCancel.createAnimationAppear(accelerateInterpolator);
        cancelAppearing.addListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        cancelAppearing.start();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        actionCancel.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animator animator) {}

    @Override
    public void onAnimationCancel(Animator animator) {}

    @Override
    public void onAnimationRepeat(Animator animator) {}

    public void onPublishCancel() {
        if (cancelAppearing.isRunning()) {
            cancelAppearing.cancel();
        }
    }

    public void onPublishSucceed() {
        if (cancelAppearing.isRunning()) {
            cancelAppearing.end();
        }
        backwardsCreateNew = true;
        backwardsRetry = false;
        progressBar.overDrawProgress = true;
        progressBar.setVisibility(VISIBLE);
        loaderText.setText(R.string.main_publication_succeed);
        loaderImage.setVisibility(VISIBLE);
        loaderImage.setImageResource(R.drawable.ic_loader_success);
        actionBackwards.setVisibility(VISIBLE);
        actionBackwards.setText(R.string.main_button_create);
        actionCancel.setVisibility(GONE);
    }

    public void onPublishFailed() {
        if (cancelAppearing.isRunning()) {
            cancelAppearing.end();
        }
        backwardsCreateNew = false;
        backwardsRetry = true;
        progressBar.overDrawProgress = true;
        progressBar.setVisibility(INVISIBLE);
        loaderText.setText(R.string.main_publication_failed);
        loaderImage.setVisibility(VISIBLE);
        loaderImage.setImageResource(R.drawable.ic_sentiment_dissatisfied);
        actionBackwards.setVisibility(VISIBLE);
        actionBackwards.setText(R.string.main_button_retry);
        actionCancel.setVisibility(GONE);
    }

    public void onPublishRetry() {
        backwardsCreateNew = false;
        backwardsRetry = false;
        progressBar.overDrawProgress = false;
        progressBar.setVisibility(VISIBLE);
        loaderText.setText(R.string.main_publication_progress);
        loaderImage.setVisibility(INVISIBLE);
        actionBackwards.setVisibility(GONE);
        actionCancel.setVisibility(VISIBLE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        backwardsCreateNew = false;
        backwardsRetry = false;
        progressBar.overDrawProgress = false;
        progressBar.setVisibility(VISIBLE);
        loaderText.setText(R.string.main_publication_progress);
        loaderImage.setVisibility(INVISIBLE);
        actionBackwards.setVisibility(GONE);
        actionCancel.setVisibility(INVISIBLE);
        unbinder.unbind();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
