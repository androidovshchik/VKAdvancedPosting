package rf.androidovshchik.vkadvancedposting.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class SelectableImageView extends AppCompatImageView {

    private static final int RECT_SIZE = ViewUtil.dp2px(2);
    private static final int CORNER_RADIUS = ViewUtil.dp2px(4);

    public boolean isSelectEnabled = true;
    public boolean isSelected = false;

    private Paint paint;

    private RectF rectOuter;
    private RectF rectCorners;

    private int colorInner;
    private int colorOuter;

    public SelectableImageView(Context context, boolean enableSelect) {
        super(context);
        isSelectEnabled = enableSelect;
        if (isSelectEnabled) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(RECT_SIZE);
            colorInner = ContextCompat.getColor(getApplicationContext(), R.color.white);
            colorOuter = ContextCompat.getColor(getApplicationContext(),
                    R.color.cornflower_blue_two);
            rectOuter = new RectF();
            rectCorners = new RectF();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isSelectEnabled) {
            return;
        }
        if (isSelected) {
            paint.setColor(colorInner);
            canvas.drawRect(RECT_SIZE * 1.5f, RECT_SIZE * 1.5f, getWidth() - RECT_SIZE * 1.5f,
                    getHeight() - RECT_SIZE * 1.5f, paint);
            paint.setColor(colorOuter);
            rectOuter.set(RECT_SIZE / 2, RECT_SIZE / 2, getWidth() - RECT_SIZE / 2,
                    getHeight() - RECT_SIZE / 2);
            canvas.drawRoundRect(rectOuter, CORNER_RADIUS, CORNER_RADIUS, paint);
        }
        paint.setColor(Color.WHITE);
        rectCorners.set(- RECT_SIZE / 2, - RECT_SIZE / 2, getWidth() + RECT_SIZE / 2,
                getHeight() + RECT_SIZE / 2);
        // 1.2f is a quick fix for some visible pixels in corners
        canvas.drawRoundRect(rectCorners, CORNER_RADIUS * 1.2f, CORNER_RADIUS * 1.2f, paint);
    }

    private Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
