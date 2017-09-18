package rf.androidovshchik.vkadvancedposting.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

import rf.androidovshchik.vkadvancedposting.events.text.KeyBackEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;

public class PostEditText extends AppCompatEditText {

    public PostEditText(Context context) {
        super(context, null);
    }

    public PostEditText(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public PostEditText(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPostText(String text) {

    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode() &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            EventUtil.post(new KeyBackEvent());
        }
        return true;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
