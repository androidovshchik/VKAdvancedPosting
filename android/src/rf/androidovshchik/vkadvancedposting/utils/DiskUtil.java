package rf.androidovshchik.vkadvancedposting.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public final class DiskUtil {

    @SuppressWarnings("all")
    public static String getExternalPhotoDir(final Context context) {
        String dirPath = null;
        File photoDir = null;
        final File externalPublicDir
          	= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (externalPublicDir.canWrite()) {
        	photoDir = new File(externalPublicDir.getPath() + "/" + context.getPackageName());
        } else {
    	    final File extStorageDir = Environment.getExternalStorageDirectory();
    	    if (extStorageDir.canWrite()) {
    	    	photoDir = new File(extStorageDir.getPath() + "/" + context.getPackageName());
    	    }
        }
        if (photoDir != null) {
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            if (photoDir.canWrite()) {
                dirPath = photoDir.getPath();
            }
        }
        return dirPath;
    }

    @SuppressWarnings("all")
    public static String getInternalPhotoDir(final Context context) {
        String dirPath = null;
       	final File photoDir = context.getDir("photo", Context.MODE_PRIVATE);
        if (photoDir != null) {
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            if (photoDir.canWrite()) {
                dirPath = photoDir.getPath();
            }
        }
        return dirPath;
    }
}
