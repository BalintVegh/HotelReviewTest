package vegh.balint.hotelreviewtest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Functions to get images
 */
public class ImageHelper {

    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    public static final int SELECT_PICTURES_REQUEST_CODE = 2;

    /**
     * Check if the Read external storage permission granted
     */
    public static boolean hasPermissionToReadImages(Context context){
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            return permissionCheck == PackageManager.PERMISSION_GRANTED;
        }else {
            return true;
        }
    }

    /**
     * Request to Read external storage permission
     */
    public static void addPermissionToReadImages(Activity activity){
        if(!hasPermissionToReadImages(activity)){
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    /**
     * Start the image selection
     * Multiple items allowed
     */
    public static void selectImages(Activity activity){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES_REQUEST_CODE);
    }

    /**
     * Get the file path from the uri
     */
    public static String getImageFilePath(Uri uri, Context context) {
        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        String imagePath = null;
        Cursor cursor = context.getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return imagePath;
    }

    /**
     * Get the file MimeType
     */
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
