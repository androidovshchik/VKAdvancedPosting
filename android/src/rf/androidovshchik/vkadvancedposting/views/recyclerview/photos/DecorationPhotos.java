package rf.androidovshchik.vkadvancedposting.views.recyclerview.photos;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

@SuppressWarnings("all")
public class DecorationPhotos extends RecyclerView.ItemDecoration {

    public static final int MAX_TOP_SPACE = ViewUtil.dp2px(16);
    private static final int TOP_SPACE = 0;
    // bottom max space may be increased or decreased by 1 px
    public static final int MIN_MAX_BOTTOM_SPACE = MAX_TOP_SPACE;
    public static final int BOTTOM_SPACE = ViewUtil.dp2px(8);
    private static final int MAX_LEFT_SPACE = MAX_TOP_SPACE;
    private static final int LEFT_SPACE = TOP_SPACE;
    private static final int MAX_RIGHT_SPACE = MAX_TOP_SPACE;
    private static final int RIGHT_SPACE = BOTTOM_SPACE;

    public DecorationPhotos() {}

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemsCount = parent.getAdapter().getItemCount();
        int columnsCount = itemsCount / 2 + (itemsCount % 2 != 0 ? 1 : 0);
        int minRightItemPosition = (columnsCount - 1) * 2;
        outRect.top = TOP_SPACE;
        outRect.bottom = BOTTOM_SPACE;
        if (position < 2) {
            outRect.left = MAX_LEFT_SPACE;
        } else {
            outRect.left = LEFT_SPACE;
        }
        if (position >= minRightItemPosition) {
            outRect.right = MAX_RIGHT_SPACE;
        } else {
            outRect.right = RIGHT_SPACE;
        }
    }
}