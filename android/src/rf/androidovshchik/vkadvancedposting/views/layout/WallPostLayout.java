package rf.androidovshchik.vkadvancedposting.views.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import rf.androidovshchik.vkadvancedposting.R;

public class WallPostLayout extends RelativeLayout {

    @BindView(R.id.progressBar)
    protected MaterialProgressBar progressBar;

    private Unbinder unbinder;

    public WallPostLayout(Context context) {
        super(context);
    }

    public WallPostLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WallPostLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public WallPostLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
