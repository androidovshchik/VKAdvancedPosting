package rf.androidovshchik.vkadvancedposting.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import timber.log.Timber;

public final class CameraUtil {

    @SuppressWarnings("all")
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyyMMdd_HHmmss");

    private static Uri getPhotoUri(Context context, String prefix) {
        GregorianCalendar today = new GregorianCalendar();
        String title = dateFormat.format(today.getTime());
        String dirPath;
        boolean isExternalStorage = true;
		dirPath = DiskUtil.getExternalPhotoDir(context);
		if (TextUtils.isEmpty(dirPath)) {
			dirPath = DiskUtil.getInternalPhotoDir(context);
			isExternalStorage = false;
		}
		String fileName = (TextUtils.isEmpty(prefix) ? title : prefix + title) + ".jpg";
        String path = dirPath + "/" + fileName;
        File file = new File(path);
        ContentValues values = new ContentValues(6);
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
        if (isExternalStorage) {
            uri = context.getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            uri = context.getContentResolver()
                    .insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        return uri;
    }

    public static Intent getCameraIntent(Context context, String prefix) {
        Intent intent = new Intent();
    	intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri(context, prefix));
    	return intent;
    }

    public static int getOrientation(String path) {
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(path);
        } catch (Exception e) {
            Timber.e(e.getMessage());
            return 0;
        }
        int exifR = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        int orientation;
        switch (exifR) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                orientation = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                orientation = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                orientation = 270;
                break;
            default:
                orientation = 0;
                break;
        }
        return orientation;
    }
}
