package rf.androidovshchik.vkadvancedposting.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

public final class DiskUtil {

    @Nullable
	public static File getFile(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("stickers/1.png");
            File file = new File(context.getCacheDir(), "output.png");
            OutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
            output.close();
            return file;
        } catch (IOException e) {
            Timber.e(e.getMessage());
        }
        return null;
    }

    @Nullable
    public static Bitmap getPhoto(Context context) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open("stickers/1.png"));
        } catch (IOException e) {
            Timber.e(e.getMessage());
        }
        return null;
    }
}
