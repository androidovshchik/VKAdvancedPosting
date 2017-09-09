package rf.androidovshchik.vkadvancedposting.themes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class DecorationThemes extends RecyclerView.ItemDecoration {

    public static final int MIN_ONE_SIDE_SPACE = ViewUtil.dp2px(12);
    public static final int SPACE_BETWEEN_ITEMS = ViewUtil.dp2px(8);

    private int topItemsMaxPosition;
    private int bottomItemsMinPosition;

    public DecorationThemes(int itemsCount, int itemsPerLine) {
        this.topItemsMaxPosition = itemsPerLine - 1;
        int linesCount = itemsCount / itemsPerLine + (itemsCount % itemsPerLine != 0 ? 1 : 0);
        this.bottomItemsMinPosition = (linesCount - 1) * itemsPerLine;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.right = SPACE_BETWEEN_ITEMS;
        outRect.top = parent.getChildAdapterPosition(view) <= topItemsMaxPosition ? MIN_ONE_SIDE_SPACE : 0;
        outRect.bottom = SPACE_BETWEEN_ITEMS;
        if (parent.getChildAdapterPosition(view) >= bottomItemsMinPosition) {
            outRect.bottom = MIN_ONE_SIDE_SPACE;
        } else {
            outRect.bottom = SPACE_BETWEEN_ITEMS;
        }
    }
}