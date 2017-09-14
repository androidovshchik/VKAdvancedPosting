package rf.androidovshchik.vkadvancedposting.views.recyclerview.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public abstract class BaseRecyclerView extends RecyclerView {

    protected final int deviceWidth;

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        deviceWidth = ViewUtil.getScreen(getApplicationContext()).x;
        init();
    }

    protected abstract void init();

    protected void setupLinearLayoutManager(boolean vertical) {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), vertical ?
                        GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL, false);
        setLayoutManager(linearLayoutManager);
    }

    protected void setupGridLayoutManager(int spanCount, boolean vertical) {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), spanCount, vertical ?
                        GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL, false);
        setLayoutManager(gridLayoutManager);
    }

    protected void setupCacheProperties(int cacheSize) {
        setDrawingCacheEnabled(true);
        setItemViewCacheSize(cacheSize);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
    }

    protected Context getApplicationContext() {
        return getContext().getApplicationContext();
    }
}
