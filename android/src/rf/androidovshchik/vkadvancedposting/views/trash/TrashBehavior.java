package rf.androidovshchik.vkadvancedposting.views.trash;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TrashBehavior extends CoordinatorLayout.Behavior<TrashFab> {

    @SuppressWarnings("unused")
    public TrashBehavior() {}

    @SuppressWarnings("unused")
    public TrashBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinator, TrashFab child,
                                         MotionEvent event) {
        child.animateTrash(event.getX(), event.getY());
        return false;
    }
}