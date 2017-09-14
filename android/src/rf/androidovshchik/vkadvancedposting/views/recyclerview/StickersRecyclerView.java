package rf.androidovshchik.vkadvancedposting.views.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import rf.androidovshchik.vkadvancedposting.adapters.stickers.AdapterStickers;

public class StickersRecyclerView extends BaseRecyclerView<AdapterStickers> {

    public StickersRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickersRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {

    }
}
