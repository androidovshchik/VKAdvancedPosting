package rf.androidovshchik.vkadvancedposting.views.layout;

import android.animation.AnimatorSet;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.fab.TrashFab;
import rf.androidovshchik.vkadvancedposting.views.layout.toolbar.BottomToolbarLayout;
import rf.androidovshchik.vkadvancedposting.views.layout.toolbar.TopToolbarLayout;

public class MainLayout extends CoordinatorLayout {

    @BindView(R.id.topToolbarLayout)
    public TopToolbarLayout topToolbar;
    @BindView(R.id.actionTrash)
    public TrashFab trashFab;
    @BindView(R.id.world)
    public FrameLayout world;
    @BindView(R.id.bottomToolbarLayout)
    public BottomToolbarLayout bottomToolbar;

    private boolean isPostMode = true;

    private int windowWidth;
    private int windowHeight;
    private int availableHeight;
    private int toolbarTopHeight;
    private int toolbarBottomHeight;

    private Unbinder unbinder;

    private AnimatorSet animatorSet;

    public MainLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        windowWidth = ViewUtil.getWindow(getApplicationContext()).x;
        windowHeight = ViewUtil.getWindow(getApplicationContext()).y;
        toolbarTopHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_top_height);
        toolbarBottomHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_bottom_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(topToolbar.sliderTranslationX, topToolbar.sliderScaleX,
                topToolbar.alphaPost, topToolbar.alphaHistory, topToolbar.alphaBackground,
                bottomToolbar.alphaBackground);
        post(new Runnable() {
            @Override
            public void run() {
                topToolbar.startSlideAnimation(true, isPostMode,
                        bottomToolbar.alphaBackground, animatorSet);
            }
        });
    }

    public void onPostMode() {
        if (!isPostMode) {
            isPostMode = true;
            topToolbar.startSlideAnimation(false, true,
                    bottomToolbar.alphaBackground, animatorSet);
            onViewportChange(null);
        }
    }

    public void onHistoryMode() {
        if (isPostMode) {
            isPostMode = false;
            topToolbar.startSlideAnimation(false, false,
                    bottomToolbar.alphaBackground, animatorSet);
            onViewportChange(null);
        }
    }

    public void onViewportChange(Integer availableHeight) {
        if (availableHeight != null) {
            this.availableHeight = availableHeight;
        }
        int notAvailableHeight = windowHeight - this.availableHeight;
        float scale;
        if (windowWidth > getViewportHeight()) {
            scale = 1f * getViewportHeight() / windowWidth;
        } else {
            scale = 1f;
        }
        world.setY(- notAvailableHeight / 2 - (notAvailableHeight > 0 && isPostMode ?
                (toolbarBottomHeight - toolbarTopHeight) / 2 : 0));
        world.setScaleX(scale);
        world.setScaleY(scale);
    }

    private int getViewportHeight() {
        return availableHeight - (isPostMode ? toolbarTopHeight + toolbarBottomHeight : 0);
    }

    private Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
