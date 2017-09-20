package rf.androidovshchik.vkadvancedposting.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class CameraIntentHelper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);


    public static Uri getPhotoUri(Context context, String prefix) {
    	final GregorianCalendar today = new GregorianCalendar(); 
        final String title = dateFormat.format(today.getTime());
        String dirPath;
        boolean isExternalStorage = true;
		dirPath = DiskUtils.getExternalPhotoDir(context);
		if (TextUtils.isEmpty(dirPath)) {
			dirPath = DiskUtils.getInternalPhotoDir(context);
			isExternalStorage = false;
		}
        final String fileName = (TextUtils.isEmpty(prefix) ? title : prefix + title) + ".jpg" ;
        final String path = dirPath + "/" + fileName;
        final File file = new File(path);
        final ContentValues values = new ContentValues(6);
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, path);
        values.put(MediaStore.Images.Media.DATE_TAKEN, today.getTimeInMillis());
        values.put(MediaStore.Images.Media.DATE_MODIFIED, today.getTimeInMillis() / 1000L);  
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length());
        }
        Uri uri;
        if (isExternalStorage)
        	uri = context.getContentResolver()
        		.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        else
        	uri = context.getContentResolver()
        	.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        return uri;
    }

    public static Intent getCameraIntent(Context context, String prefix) {
    	final Intent intent = new Intent();
    	intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri(context, prefix));
    	return intent;
    }
}
