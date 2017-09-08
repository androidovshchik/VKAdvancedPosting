package rf.androidovshchik.vkadvancedposting.stickers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class DecorationStickers extends RecyclerView.ItemDecoration {

    public static final int MIN_ONE_SIDE_SPACE = ViewUtil.dp2px(12);
    public static final int SPACE_BETWEEN_ITEMS = ViewUtil.dp2px(8);

    private int itemsPerLine;
    private int linesCount;

    public DecorationStickers(int itemsCount, int itemsPerLine) {
        this.itemsPerLine = itemsPerLine;
        this.linesCount = itemsCount / itemsPerLine;
        if (itemsCount % itemsPerLine != 0) {
            this.linesCount++;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.right = SPACE_BETWEEN_ITEMS;
        outRect.top = parent.getChildAdapterPosition(view) < itemsPerLine ? MIN_ONE_SIDE_SPACE : 0;
        outRect.bottom = SPACE_BETWEEN_ITEMS;
        if (parent.getChildAdapterPosition(view) >= ((linesCount - 1) * itemsPerLine)) {
            outRect.bottom = MIN_ONE_SIDE_SPACE;
        } else {
            outRect.bottom = SPACE_BETWEEN_ITEMS;
        }
    }
}