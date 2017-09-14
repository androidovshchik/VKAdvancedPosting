package rf.androidovshchik.vkadvancedposting.views.recyclerview.themes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class DecorationThemes extends RecyclerView.ItemDecoration {

    private static final int TOP_SPACE = ViewUtil.dp2px(12);
    private static final int BOTTOM_SPACE = TOP_SPACE;
    private static final int MAX_LEFT_SPACE = ViewUtil.dp2px(16);
    private static final int LEFT_SPACE = 0;
    private static final int MAX_RIGHT_SPACE = ViewUtil.dp2px(16);
    private static final int RIGHT_SPACE = ViewUtil.dp2px(12);

    public DecorationThemes() {}

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemsCount = parent.getChildCount();
        outRect.top = TOP_SPACE;
        outRect.bottom = BOTTOM_SPACE;
        if (position == 0) {
            outRect.left = MAX_LEFT_SPACE;
        } else {
            outRect.left = LEFT_SPACE;
        }
        if (position == itemsCount - 1) {
            outRect.right = MAX_RIGHT_SPACE;
        } else {
            outRect.right = RIGHT_SPACE;
        }
    }
}