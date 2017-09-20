package rf.androidovshchik.vkadvancedposting.views.fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import timber.log.Timber;

public class TrashFab extends AppCompatImageView {

    private static final int DEFAULT_VISIBLE_MARGIN_BOTTOM = ViewUtil.dp2px(16);
    private static final int DEFAULT_RADIUS_OPEN = ViewUtil.dp2px(48);

    private PointF trashCenter;

    public int marginBottom;

    public int visibleMarginBottom;
    private int radiusOpen;

    public boolean stickerPressed = false;

    public TrashFab(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrashFab(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int toolbarBottomHeight =
                getResources().getDimensionPixelSize(R.dimen.toolbar_bottom_height);
        Timber.d("toolbarBottomHeight: %dpx", toolbarBottomHeight);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrashFab,
                defStyleAttr, 0);
        visibleMarginBottom = array.getDimensionPixelSize(R.styleable.TrashFab_visibleMarginBottom,
                DEFAULT_VISIBLE_MARGIN_BOTTOM);
        Timber.d("visibleMarginBottom: %dpx", visibleMarginBottom);
        marginBottom = visibleMarginBottom;
        Timber.d("marginBottom: %dpx", marginBottom);
        radiusOpen = array.getDimensionPixelSize(R.styleable.TrashFab_radiusOpen,
                DEFAULT_RADIUS_OPEN);
        Timber.d("radiusOpen: %dpx", radiusOpen);
        array.recycle();
        setTag(0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        trashCenter = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        trashCenter.x = getX() + w / 2;
        Timber.d("X: %d - %f - %d", getLeft(), trashCenter.x, getRight());
        Timber.d("Y: %d - %f - %d", getTop(), trashCenter.y, getBottom());
    }

    public void animateTrash(float touchX, float touchY) {
        if (!stickerPressed) {
            if (getVisibility() == VISIBLE) {
                setVisibility(INVISIBLE);
            }
            return;
        }
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
            trashCenter.y = getY() + getHeight() / 2;
        }
        double touchDistance = Math.hypot(touchX - trashCenter.x, touchY - trashCenter.y);
        if (touchDistance < radiusOpen && (int) getTag() != R.drawable.ic_fab_trash_released) {
            setTag(R.drawable.ic_fab_trash_released);
            setImageResource(R.drawable.ic_fab_trash_released);
            setScaleY(1.5f);
            setScaleX(1.5f);
        } else if (touchDistance > radiusOpen && (int) getTag() != R.drawable.ic_fab_trash) {
            setTag(R.drawable.ic_fab_trash);
            setImageResource(R.drawable.ic_fab_trash);
            setScaleY(1f);
            setScaleX(1f);
        }
    }
}