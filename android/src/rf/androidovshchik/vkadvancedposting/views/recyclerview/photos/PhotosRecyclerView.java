package rf.androidovshchik.vkadvancedposting.views.recyclerview.photos;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.AttributeSet;

import java.io.File;

import rf.androidovshchik.vkadvancedposting.components.CursorPhotos;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.BaseRecyclerView;

public class PhotosRecyclerView extends BaseRecyclerView
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CURSOR_PHOTOS = 1;

    private static final int DIVIDER_HEIGHT = ViewUtil.dp2px(0.5f);

    private Paint paint;

    public AdapterPhotos adapterPhotos;

    public PhotosRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotosRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(DIVIDER_HEIGHT);
        paint.setColor(Color.parseColor("#1e000000"));
        adapterPhotos = new AdapterPhotos();
        setAdapter(adapterPhotos);
        setupGridLayoutManager(2, false);
        LoaderManager loaderManager = getSupportLoaderManager();
        if (loaderManager != null) {
            if (loaderManager.getLoader(CURSOR_PHOTOS) != null) {
                loaderManager.restartLoader(CURSOR_PHOTOS, null, this);
            } else {
                loaderManager.initLoader(CURSOR_PHOTOS, null, this);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, DIVIDER_HEIGHT / 2, getWidth(), DIVIDER_HEIGHT / 2, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        int itemHeight = AdapterPhotos.ITEM_SIZE;
        int maxBottomSpace = DecorationPhotos.MIN_MAX_BOTTOM_SPACE;
        int viewHeight = getHeight();
        int normalViewHeight = itemHeight * 2 + DecorationPhotos.BOTTOM_SPACE
                + DecorationPhotos.MAX_TOP_SPACE + maxBottomSpace;
        if (viewHeight > normalViewHeight) {
            while (normalViewHeight + 2 <= viewHeight) {
                normalViewHeight += 2;
                itemHeight++;
            }
            if (normalViewHeight != viewHeight) {
                // 1px difference
                maxBottomSpace++;
            }
        } else if (viewHeight < normalViewHeight) {
            while (normalViewHeight - 2 >= viewHeight) {
                normalViewHeight -= 2;
                itemHeight--;
            }
            if (normalViewHeight != viewHeight) {
                // 1px difference
                maxBottomSpace--;
            }
        }
        adapterPhotos.itemSize = itemHeight;
        setPadding(0, DecorationPhotos.MAX_TOP_SPACE, 0,
                maxBottomSpace - DecorationPhotos.BOTTOM_SPACE);
        addItemDecoration(new DecorationPhotos());
        setupCacheProperties();
        adapterPhotos.hasUiPrepared = true;
        if (adapterPhotos.getItemCount() > 0) {
            adapterPhotos.notifyDataSetChanged();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorPhotos(getApplicationContext(), MediaStore.Images.Media.DATA);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                adapterPhotos.photoPaths.add(new File(path));
            } while (cursor.moveToNext());
            if (adapterPhotos.hasUiPrepared) {
                adapterPhotos.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
