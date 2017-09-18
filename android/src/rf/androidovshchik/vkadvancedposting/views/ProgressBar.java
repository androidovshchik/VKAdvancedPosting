package rf.androidovshchik.vkadvancedposting.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class ProgressBar extends MaterialProgressBar {

    private static final int CIRCLE_RADIUS = ViewUtil.dp2px(24);
    private static final int CIRCLE_WIDTH = ViewUtil.dp2px(4.5f);

    public boolean overDrawProgress = false;

    private Paint paint;

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }

    private void onInit() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(CIRCLE_WIDTH);
        paint.setColor(ContextCompat.getColor(getApplicationContext(),
                R.color.cornflower_blue_two));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (overDrawProgress) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, CIRCLE_RADIUS -
                    1f * CIRCLE_WIDTH / 2, paint);
        }
    }

    private Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
