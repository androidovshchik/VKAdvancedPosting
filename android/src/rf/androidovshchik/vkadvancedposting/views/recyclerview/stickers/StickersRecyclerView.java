package rf.androidovshchik.vkadvancedposting.views.recyclerview.stickers;

import android.content.Context;
import android.util.AttributeSet;

import java.io.IOException;

import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.BaseRecyclerView;
import timber.log.Timber;

public class StickersRecyclerView extends BaseRecyclerView<AdapterStickers> {

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
        final int maxGridWidth = deviceWidth - DecorationStickers.MIN_ONE_SIDE_SPACE * 2 +
                DecorationStickers.SPACE_BETWEEN_ITEMS;
        int minItemWidth = AdapterStickers.MIN_ITEM_SIZE + DecorationStickers.SPACE_BETWEEN_ITEMS;
        final int itemsPerLine = maxGridWidth / minItemWidth;
        int freeSpace = maxGridWidth - itemsPerLine * minItemWidth;
        while (freeSpace >= itemsPerLine) {
            minItemWidth++;
            freeSpace -= itemsPerLine;
        }
        int leftPadding = DecorationStickers.MIN_ONE_SIDE_SPACE + freeSpace / 2;
        int rightPadding = DecorationStickers.MIN_ONE_SIDE_SPACE -
                DecorationStickers.SPACE_BETWEEN_ITEMS + freeSpace / 2 + (freeSpace > 0 &&
                freeSpace % 2 != 0 ? 1 : 0);
        adapter = new AdapterStickers(itemsCount, minItemWidth -
                DecorationStickers.SPACE_BETWEEN_ITEMS);
        setupGridLayoutManager(2, true);
    }
}
