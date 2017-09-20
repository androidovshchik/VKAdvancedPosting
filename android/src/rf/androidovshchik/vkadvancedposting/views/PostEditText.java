package rf.androidovshchik.vkadvancedposting.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;

import rf.androidovshchik.vkadvancedposting.events.text.KeyBackEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class PostEditText extends AppCompatEditText implements TextWatcher {

    private static final int RADIUS = ViewUtil.dp2px(8);
    private static final int PADDING = ViewUtil.dp2px(4);

    public int backgroundColor = Color.TRANSPARENT;

    private int windowWidth;

    private int maxLines;

    private String beforeText;

    private Paint paint;
    private Path path;

    public PostEditText(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public PostEditText(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        windowWidth = ViewUtil.getWindow(getApplicationContext()).x;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(RADIUS));
        path = new Path();
        addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {}

    @Override
    public void beforeTextChanged(CharSequence text, int start, int count, int after) {
        beforeText = text.toString();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().isEmpty()) {
            setGravity(Gravity.START);
        } else {
            setGravity(Gravity.CENTER_HORIZONTAL);
        }
        int linesCount = countLines(editable.toString());
        if (linesCount > maxLines || linesCount == maxLines && getWidth() >= windowWidth) {
            removeTextChangedListener(this);
            setText("");
            if (getWidth() >= windowWidth) {
                beforeText = beforeText.substring(0, beforeText.length() - 1);
            }
            append(beforeText);
            addTextChangedListener(this);
        }
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            EventUtil.post(new KeyBackEvent());
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getText().toString().isEmpty()) {
            super.onDraw(canvas);
            return;
        }
        if (backgroundColor == Color.TRANSPARENT) {
            super.onDraw(canvas);
            return;
        }
        paint.setColor(backgroundColor);
        Layout layout = getLayout();
        float x = getWidth() / 2;
        float y = 0;
        path.reset();
        path.moveTo(x, y);
        for (int i = 0; i < layout.getLineCount(); i++) {
            x = 1f * getWidth() / 2 - layout.getLineWidth(i) / 2 - PADDING;
            path.lineTo(x, y);
            y += layout.getLineBottom(i) - layout.getLineTop(i);
            path.lineTo(x, y);
        }
        x = getWidth() / 2;
        path.lineTo(x, y);
        for (int i = layout.getLineCount() - 1; i >= 0; i--) {
            x = 1f * getWidth() / 2 + layout.getLineWidth(i) / 2 + PADDING;
            path.lineTo(x, y);
            y -= layout.getLineBottom(i) - layout.getLineTop(i);
            path.lineTo(x, y);
        }
        x = getWidth() / 2;
        path.lineTo(x, y);
        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        maxLines = windowWidth / getLineHeight() - 1;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Layout layout = getLayout();
        int line = layout.getLineForVertical(Math.round(event.getY()));
        float lineStartX = 1f * layout.getWidth() / 2 - layout.getLineWidth(line) / 2;
        float lineEndX = 1f * layout.getWidth() / 2 + layout.getLineWidth(line) / 2;
        return (!(event.getX() < lineStartX || event.getX() > lineEndX) ||
                getText().toString().isEmpty()) && super.dispatchTouchEvent(event);
    }

    private int countLines(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }
        int lines = 1, position = 0;
        while ((position = string.indexOf("\n", position) + 1) != 0) {
            lines++;
        }
        return lines;
    }

    private Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
