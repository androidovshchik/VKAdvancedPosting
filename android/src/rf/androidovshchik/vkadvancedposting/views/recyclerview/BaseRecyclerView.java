package rf.androidovshchik.vkadvancedposting.views.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseRecyclerView<T extends RecyclerView.Adapter<?>> extends RecyclerView {

    protected T adapter;

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract void init();

    protected void setupLinearLayoutManager(boolean vertical) {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext().getApplicationContext(), vertical ?
                        GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL, false);
        setLayoutManager(linearLayoutManager);
    }

    protected void setupGridLayoutManager(int spanCount, boolean vertical) {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext().getApplicationContext(), spanCount, vertical ?
                        GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL, false);
        setLayoutManager(gridLayoutManager);
    }

    protected void setupCacheProperties(int cacheSize) {
        setDrawingCacheEnabled(true);
        setItemViewCacheSize(cacheSize);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
    }
}
