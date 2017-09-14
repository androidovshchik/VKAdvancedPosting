package rf.androidovshchik.vkadvancedposting.components;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

public class CursorPhotos extends CursorLoader {

    private String[] columns;

    public CursorPhotos(Context context, String... columns) {
        super(context);
        this.columns = columns;
    }

    @Override
    public Cursor loadInBackground() {
        return getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC LIMIT 100");
    }
}