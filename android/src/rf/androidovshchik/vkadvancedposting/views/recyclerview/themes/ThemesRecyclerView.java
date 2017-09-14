package rf.androidovshchik.vkadvancedposting.views.recyclerview.themes;

import android.content.Context;
import android.util.AttributeSet;

import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.BaseRecyclerView;

public class ThemesRecyclerView extends BaseRecyclerView {

    public ThemesRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemesRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        AdapterThemes adapter = new AdapterThemes(AdapterThemes.MIN_ITEM_SIZE);
        setAdapter(adapter);
        setupLinearLayoutManager(false);
        addItemDecoration(new DecorationThemes());
        setHasFixedSize(true);
        setupCacheProperties(adapter.itemsCount);
    }
}
