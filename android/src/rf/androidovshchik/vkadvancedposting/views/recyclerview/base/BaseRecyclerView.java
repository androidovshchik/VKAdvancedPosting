package rf.androidovshchik.vkadvancedposting.views.recyclerview.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
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
        setHasFixedSize(true);
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

    protected void setupCacheProperties() {
        setDrawingCacheEnabled(true);
        setItemViewCacheSize(8);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
    }

    @Nullable
    protected LoaderManager getSupportLoaderManager() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                return ((AppCompatActivity) context).getSupportLoaderManager();
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    protected Context getApplicationContext() {
        return getContext().getApplicationContext();
    }
}
