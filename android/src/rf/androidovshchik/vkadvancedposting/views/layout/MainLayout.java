package rf.androidovshchik.vkadvancedposting.views.layout;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.layout.toolbar.BottomToolbarLayout;
import rf.androidovshchik.vkadvancedposting.views.layout.toolbar.TopToolbarLayout;

public class MainLayout extends CoordinatorLayout
        implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean isPostMode = true;

    private int windowHeight;
    private int toolbarTopHeight;
    private int toolbarBottomHeight;
    private int worldHeight;

    @BindView(R.id.topToolbarLayout)
    public TopToolbarLayout topToolbar;
    @BindView(R.id.world)
    public View world;
    @BindView(R.id.bottomToolbarLayout)
    public BottomToolbarLayout bottomToolbar;

    private View decorView;
    private Rect rectResizedWindow;

    private Unbinder unbinder;

    public MainLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Point window = ViewUtil.getWindow(getApplicationContext());
        int windowWidth = window.x;
        windowHeight =  window.y;
        toolbarTopHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_top_height);
        toolbarBottomHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_bottom_height);
        int worldWidth = getResources().getDimensionPixelSize(R.dimen.world_width);
        worldHeight = Math.round(1f * windowHeight * worldWidth / windowWidth);
        decorView = getDecorView();
        rectResizedWindow = new Rect();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        decorView.getViewTreeObserver()
                .addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        decorView.getWindowVisibleDisplayFrame(rectResizedWindow);
        int difference = windowHeight - rectResizedWindow.bottom;
        if (difference != 0) {
            if (getPaddingBottom() != difference) {
                setPadding(0, 0, 0, difference);
            }
        } else {
            if (getPaddingBottom() != 0) {
                setPadding(0, 0, 0, 0);
            }
        }
    }

    public void setMode(boolean isPostMode) {
        this.isPostMode = isPostMode;
        if (isPostMode) {
            float scale = 1f * getViewportHeight() / windowHeight;
            //world.setY((toolbarTopHeight - toolbarBottomHeight + windowHeight - deviceHeight) / 2);
            world.setScaleX(scale);
            world.setScaleY(scale);
        } else {
            world.setY(0);
            world.setScaleX(1f);
            world.setScaleY(1f);
        }
    }

    public void setModeImmediately(boolean isPostMode) {
        this.isPostMode = isPostMode;
        if (isPostMode) {
            float scale = 1f * getViewportHeight() / worldHeight;
            //world.setY((toolbarTopHeight - toolbarBottomHeight + windowHeight - deviceHeight) / 2);
            world.setScaleX(scale);
            world.setScaleY(scale);
        } else {
            world.setY(0);
            world.setScaleX(1f);
            world.setScaleY(1f);
        }
    }

    private int getViewportHeight() {
        return windowHeight - toolbarTopHeight - toolbarBottomHeight;
    }

    @Nullable
    private View getDecorView() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                return ((AppCompatActivity) context).getWindow().getDecorView();
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        decorView.getViewTreeObserver()
                .removeOnGlobalLayoutListener(this);
    }
}
