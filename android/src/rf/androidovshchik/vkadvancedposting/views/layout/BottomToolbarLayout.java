package rf.androidovshchik.vkadvancedposting.views.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import butterknife.BindView;
import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.themes.ThemesRecyclerView;

public class BottomToolbarLayout extends ToolbarLayout {

    @BindView(R.id.themesRecyclerView)
    public ThemesRecyclerView themesRecyclerView;

    public BottomToolbarLayout(Context context) {
        super(context);
    }

    public BottomToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public BottomToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
