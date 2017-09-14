package rf.androidovshchik.vkadvancedposting.views.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import rf.androidovshchik.vkadvancedposting.adapters.themes.AdapterThemes;

public class ThemesRecyclerView extends BaseRecyclerView<AdapterThemes> {

    public ThemesRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemesRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {

    }
}
