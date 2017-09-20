package rf.androidovshchik.vkadvancedposting.views.fab;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

@SuppressWarnings("unused")
public class TrashBehavior extends CoordinatorLayout.Behavior<TrashFab>
        implements GestureDetector.OnGestureListener {

    private int windowWidth;
    private int windowHeight;

    private Rect rect;

    private GestureDetector gestureDetector;

    private boolean hasLongPressed = false;

    public TrashBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        windowWidth = ViewUtil.getWindow(context).x;
        windowHeight = ViewUtil.getWindow(context).y;
        rect = new Rect();
        gestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TrashFab fab, View dependency) {
        return dependency instanceof FrameLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TrashFab fab, View dependency) {
        dependency.getGlobalVisibleRect(rect);
        fab.setY(rect.bottom - (windowHeight - windowWidth) / 2 * dependency.getScaleY() -
                fab.getHeight() - fab.marginBottom);
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        hasLongPressed = false;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {}

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent oldEvent, MotionEvent newEvent, float x, float y) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent oldEvent, MotionEvent newEvent, float x, float y) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        hasLongPressed = true;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinator, TrashFab child,
                                         MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if (hasLongPressed) {
            if (event.getAction() != MotionEvent.ACTION_MOVE) {
                hasLongPressed = false;
                return false;
            }
            child.animateTrash(event.getX(), event.getY());
        }
        return false;
    }
}