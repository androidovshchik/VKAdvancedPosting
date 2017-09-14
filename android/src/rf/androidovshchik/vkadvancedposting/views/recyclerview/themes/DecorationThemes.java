package rf.androidovshchik.vkadvancedposting.views.recyclerview.themes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class DecorationThemes extends RecyclerView.ItemDecoration {

    private static final int VERTICAL_SPACE = ViewUtil.dp2px(12);
    private static final int MIN_RIGHT_SPACE = ViewUtil.dp2px(5);
    private static final int RIGHT_SPACE = ViewUtil.dp2px(6);
    private static final int MAX_LEFT_SPACE = ViewUtil.dp2px(16);
    private static final int LEFT_SPACE = 0;

    private int itemsCount;

    public DecorationThemes(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = outRect.bottom = VERTICAL_SPACE;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = MAX_LEFT_SPACE;
        } else {
            outRect.left = LEFT_SPACE;
        }
        if (parent.getChildAdapterPosition(view) == itemsCount - 1) {
            outRect.right = MIN_RIGHT_SPACE;
        } else {
            outRect.right = RIGHT_SPACE;
        }
    }
}