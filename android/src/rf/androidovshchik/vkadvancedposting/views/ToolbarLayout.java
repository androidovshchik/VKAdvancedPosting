package rf.androidovshchik.vkadvancedposting.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.R;

public class ToolbarLayout extends RelativeLayout {

    @BindView(R.id.slider)
    protected View slider;
    @BindView(R.id.actionPost)
    protected View actionPost;
    @BindView(R.id.actionHistory)
    protected View actionHistory;

    private Unbinder unbinder;

    public ToolbarLayout(Context context) {
        super(context);
    }

    public ToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);
    }

    public void onPostClicked() {

    }

    public void onHistoryClicked() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
