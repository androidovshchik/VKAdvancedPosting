package rf.androidovshchik.vkadvancedposting.views.trash;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import timber.log.Timber;

@SuppressWarnings("unused")
public class TrashBehavior extends CoordinatorLayout.Behavior<TrashFab>
        implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    private boolean hasLongPressed = false;

    public TrashBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Timber.d("Event: onDown");
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
        Timber.d("Event: onLongPress");
        hasLongPressed = true;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinator, TrashFab child,
                                         MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if (hasLongPressed) {
            if (event.getAction() != MotionEvent.ACTION_MOVE) {
                Timber.d("Stopping trash calls");
                hasLongPressed = false;
                return false;
            }
            child.animateTrash(event.getX(), event.getY());
        }
        return false;
    }
}