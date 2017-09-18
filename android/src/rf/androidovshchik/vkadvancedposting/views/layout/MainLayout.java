package rf.androidovshchik.vkadvancedposting.views.layout;

import android.animation.AnimatorSet;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.views.layout.toolbar.BottomToolbarLayout;
import rf.androidovshchik.vkadvancedposting.views.layout.toolbar.TopToolbarLayout;

public class MainLayout extends CoordinatorLayout {

    @BindView(R.id.topToolbarLayout)
    public TopToolbarLayout topToolbar;
    @BindView(R.id.world)
    public View world;
    @BindView(R.id.bottomToolbarLayout)
    public BottomToolbarLayout bottomToolbar;

    public boolean isPostMode = true;

    private Unbinder unbinder;

    private AnimatorSet animatorSet;

    public MainLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        animatorSet = new AnimatorSet();
    }

    public void onPostMode() {
        if (!isPostMode) {
            isPostMode = true;
            startSlideAnimation(false);
        }
    }

    public void onHistoryMode() {
        if (isPostMode) {
            isPostMode = false;
            startSlideAnimation(false);
        }
    }

    public void setModeImmediately() {
        /*if (isPostMode) {
            float scale = 1f * getViewportHeight() / windowHeight;
            world.setY((toolbarTopHeight - toolbarBottomHeight) / 2);
            world.setScaleX(scale);
            world.setScaleY(scale);
        } else {
            world.setY(0);
            world.setScaleX(1f);
            world.setScaleY(1f);
        }*/
    }

    private int getViewportHeight() {
        return 0;//windowHeight - toolbarTopHeight - toolbarBottomHeight;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
