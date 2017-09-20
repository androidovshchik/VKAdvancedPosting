package rf.androidovshchik.vkadvancedposting.components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

public class CursorPicker extends CursorLoader {

    private Uri uri;

    public CursorPicker(Context context, Uri uri) {
        super(context);
        this.uri = uri;
    }

    @Override
    public Cursor loadInBackground() {
        String[] columns = { MediaStore.Images.Media.DATA };
        return getContext().getContentResolver().query(uri, columns, null, null, null);
    }
}