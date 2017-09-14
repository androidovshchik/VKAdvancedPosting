package rf.androidovshchik.vkadvancedposting.views.recyclerview.photos;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.File;

import rf.androidovshchik.vkadvancedposting.components.CursorPhotos;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.BaseRecyclerView;

public class PhotosRecyclerView extends BaseRecyclerView
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CURSOR_PHOTOS = 1;

    private AdapterPhotos adapterPhotos;

    public PhotosRecyclerView(Context context) {
        super(context, null, 0);
    }

    @Override
    protected void init() {
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
        } else if (viewHeight < normalViewHeight) {
            while (normalViewHeight - 2 >= viewHeight) {
                normalViewHeight -= 2;
                itemHeight++;
            }
        }
        if (normalViewHeight != viewHeight) {
            // 1px difference
            maxBottomSpace++;
        }
        adapterPhotos.itemSize = itemHeight;
        addItemDecoration(new DecorationPhotos(maxBottomSpace));
        setupCacheProperties();
        // ui is prepared
        adapterPhotos.photoPickersCount = 2;
        adapterPhotos.notifyDataSetChanged();
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
            if (adapterPhotos.photoPickersCount > 0) {
                // onSizeChanged called before and ui was prepared
                adapterPhotos.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
