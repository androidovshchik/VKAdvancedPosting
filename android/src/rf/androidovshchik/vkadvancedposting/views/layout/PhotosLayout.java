package rf.androidovshchik.vkadvancedposting.views.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.photos.PhotosRecyclerView;

public class PhotosLayout extends FrameLayout {

    public View photosShadow;

    public PhotosRecyclerView photosRecyclerView;

    public PhotosLayout(Context context) {
        super(context);
    }

    public PhotosLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotosLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public PhotosLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        photosShadow = findViewById(R.id.photosShadow);
        photosRecyclerView = findViewById(R.id.photosRecyclerView);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
