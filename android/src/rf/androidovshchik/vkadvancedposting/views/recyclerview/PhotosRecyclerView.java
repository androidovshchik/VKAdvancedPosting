package rf.androidovshchik.vkadvancedposting.views.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import rf.androidovshchik.vkadvancedposting.adapters.photos.AdapterPhotos;

public class PhotosRecyclerView extends BaseRecyclerView<AdapterPhotos> {

    public PhotosRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotosRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {

    }
}
