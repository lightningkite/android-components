package com.lightningkite.androidcomponents.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.lightningkite.androidcomponents.bigview.BigView;

import java.io.IOException;

/**
 * Created by jivie on 6/8/15.
 */
public class ImageUtils {

    private static final int REQUEST_GALLERY = 839047901;
    private static final int REQUEST_CAMERA = 32847123;

    public static void startGallery(BigView view, String requestTitle, int requestCode) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, requestTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        view.startIntentForResult(chooserIntent, requestCode);
    }

    public static Bitmap onGalleryResult(Context context, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                int rotation = 0;
                String realPathFromURI = getRealPathFromURI(context, data.getData());
                ExifInterface exif = new ExifInterface(realPathFromURI);
                if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
                    rotation = 90;
                } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
                    rotation = 270;
                } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
                    rotation = 180;
                }
                return rotateImage(BitmapFactory.decodeFile(realPathFromURI, options), rotation);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void startCamera(BigView view, String requestTitle, Uri saveLocation, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, saveLocation);
            view.startIntentForResult(intent, requestCode);
        } else {
            Toast.makeText(view.getContext(), "There is no camera available.", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap onCameraResult(Context context, int resultCode, Uri saveLocation) {
        if (resultCode == Activity.RESULT_OK) {
            load(context, saveLocation);
        }
        return null;
    }

    public static Bitmap load(Context context, Uri location) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(location.getPath(), options);
    }

    static public String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static Bitmap rotateImage(Bitmap image, int degrees) {
        Matrix matrix = new Matrix();
        int w = image.getWidth();
        int h = image.getHeight();

        matrix.postRotate(degrees);

        return Bitmap.createBitmap(image, 0, 0, w, h, matrix, true);
    }
}
