package rf.androidovshchik.vkadvancedposting.views.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.themes.AdapterThemes;
import rf.androidovshchik.vkadvancedposting.themes.DecorationThemes;

public class BottomToolbarLayout extends LinearLayout {

    @BindView(R.id.bottomBackground)
    protected View background;
    @BindView(R.id.themesRecyclerView)
    protected RecyclerView recyclerView;

    private Unbinder unbinder;

    public BottomToolbarLayout(Context context) {
        super(context);
    }

    public BottomToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public BottomToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        AdapterThemes adapter = new AdapterThemes();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration(new DecorationThemes(itemsCount, itemsPerLine));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemViewCacheSize(itemsCount);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
