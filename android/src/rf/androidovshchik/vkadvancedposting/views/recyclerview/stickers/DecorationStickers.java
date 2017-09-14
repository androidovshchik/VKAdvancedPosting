package rf.androidovshchik.vkadvancedposting.views.recyclerview.stickers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

@SuppressWarnings("all")
public class DecorationStickers extends RecyclerView.ItemDecoration {

    private static final int MAX_TOP_SPACE = ViewUtil.dp2px(12);
    private static final int TOP_SPACE = 0;
    private static final int MAX_BOTTOM_SPACE = MAX_TOP_SPACE;
    private static final int BOTTOM_SPACE = ViewUtil.dp2px(8);
    // left and right max spaces summary may be increased upto (itemsCount - 1) px
    public static final int MIN_MAX_LEFT_SPACE = MAX_TOP_SPACE;
    private static final int LEFT_SPACE = TOP_SPACE;
    // right max space may be large on 1px if (itemsCount - 1) % 2 != 0
    public static final int MIN_MAX_RIGHT_SPACE = MAX_TOP_SPACE;
    public static final int RIGHT_SPACE = BOTTOM_SPACE;

    private int itemsPerLine;
    private int maxLeftSpace;
    private int maxRightSpace;

    public DecorationStickers(int itemsPerLine, int maxLeftSpace, int maxRightSpace) {
        this.itemsPerLine = itemsPerLine;
        this.maxLeftSpace = maxLeftSpace;
        this.maxRightSpace = maxRightSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemsCount = parent.getAdapter().getItemCount();
        int linesCount = itemsCount / itemsPerLine + (itemsCount % itemsPerLine != 0 ? 1 : 0);
        int maxTopItemPosition = itemsPerLine - 1;
        int minBottomItemPosition = (linesCount - 1) * itemsPerLine;
        if (position <= maxTopItemPosition) {
            outRect.top = MAX_TOP_SPACE;
        } else {
            outRect.top = TOP_SPACE;
        }
        if (position >= minBottomItemPosition) {
            outRect.bottom = MAX_BOTTOM_SPACE;
        } else {
            outRect.bottom = BOTTOM_SPACE;
        }
        if (position % itemsPerLine == 0) {
            outRect.left = maxLeftSpace;
        } else {
            outRect.left = LEFT_SPACE;
        }
        if ((position + 1) % itemsPerLine == 0) {
            outRect.right = maxRightSpace;
        } else {
            outRect.right = RIGHT_SPACE;
        }
    }
}