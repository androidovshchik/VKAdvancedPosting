package rf.androidovshchik.vkadvancedposting.views;

import android.content.Context;
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

    private int windowWidth;

    private int maxLines;

    private String beforeText;

    public PostEditText(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public PostEditText(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        windowWidth = ViewUtil.getWindow(getApplicationContext()).x;
        addTextChangedListener(this);
    }

    public void setPostText(String text) {

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
        return !(event.getX() < lineStartX || event.getX() > lineEndX)
                && super.dispatchTouchEvent(event);
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
