package rf.androidovshchik.vkadvancedposting.views.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

public class StickersLayout extends LinearLayoutCompat {

    public BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
            };

    public StickersLayout(Context context) {
        super(context);
    }

    public StickersLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickersLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
