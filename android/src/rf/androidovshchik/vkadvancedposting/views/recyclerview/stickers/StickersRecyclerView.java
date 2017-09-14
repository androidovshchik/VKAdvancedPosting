package rf.androidovshchik.vkadvancedposting.views.recyclerview.stickers;

import android.content.Context;
import android.util.AttributeSet;

import java.io.IOException;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.BaseRecyclerView;
import timber.log.Timber;

public class StickersRecyclerView extends BaseRecyclerView {

    public StickersRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickersRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        final int itemsCount;
        try {
            itemsCount = getApplicationContext().getResources().getAssets().list("stickers").length;
        } catch (IOException e) {
            Timber.e(e.getMessage());
            return;
        }
        int deviceWidth = ViewUtil.getScreen(getApplicationContext()).x;
        int maxLeftSpace = DecorationStickers.MIN_MAX_LEFT_SPACE;
        int maxRightSpace = DecorationStickers.MIN_MAX_RIGHT_SPACE;
        int rightSpace = DecorationStickers.RIGHT_SPACE;
        int itemWidth = AdapterStickers.MIN_ITEM_SIZE;
        int maxGridWidth = deviceWidth - maxLeftSpace - maxRightSpace;
        int itemsPerLine = maxGridWidth / (itemWidth + rightSpace);
        int freeSpace = maxGridWidth - itemsPerLine * (itemWidth + rightSpace);
        while (freeSpace >= itemsPerLine) {
            itemWidth++;
            freeSpace -= itemsPerLine;
        }
        if (freeSpace > 0) {
            maxLeftSpace += freeSpace / 2;
            maxRightSpace += freeSpace / 2 + (freeSpace % 2 != 0 ? 1 : 0);
        }
        AdapterStickers adapter = new AdapterStickers(itemsCount, itemWidth);
        setAdapter(adapter);
        setupGridLayoutManager(itemsPerLine, true);
        addItemDecoration(new DecorationStickers(itemsPerLine, maxLeftSpace, maxRightSpace));
        setupCacheProperties();
    }
}
