package rf.androidovshchik.vkadvancedposting.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class PostEditText extends AppCompatEditText {

    private KeyImeChangeListener keyImeChangeListener;

    public PostEditText(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public PostEditText(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyImeChangeListener(KeyImeChangeListener listener) {
        keyImeChangeListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyImeChangeListener != null && KeyEvent.KEYCODE_BACK == event.getKeyCode() &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            keyImeChangeListener.onBackKeyboardPressed();
        }
        return true;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public interface KeyImeChangeListener {

        void onBackKeyboardPressed();
    }
}
