package com.example.cameraoperating;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.karumi.dexter.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtils {
    public static void refreshGallery(Context context, String filePath)
    {
        MediaScannerConnection.scanFile(context,
                new String[]{filePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
    public static boolean checkPermissions(Context context)
    {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;


    }
    public static Bitmap optimizeBitmap(int sampleSize, String filePath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static boolean isDeviceSupportCamera(Context context)
    {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    public static void openSettings(Context context)
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
    public static Uri getOutputMediaFileUri(Context context, File file){
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider",file);
    }
    public static File getOutputMediaFile(int type)
    {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    MainActivity.GALLERY_DIRECTORY_NAME);
            /*if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                {
                    Log.e(MainActivity.GALLERY_DIRECTORY_NAME,"Oops! Failed create." +
                            MainActivity.GALLERY_DIRECTORY_NAME + "directory");
                    return null;
                }

            }
*/

        if (mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                mediaStorageDir.mkdir();
            }
        }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).
                    format(new Date());
                    File mediaFile;
                    if (type == MainActivity.MEDIA_TYPE_VIDEO) {
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_"
                                + timeStamp + "." + MainActivity.VIDEO_EXTENSION);
                    }
                    else if(type == MainActivity.MEDIA_TYPE_IMAGE) {
                        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_"
                                + timeStamp + "." + MainActivity.IMAGE_EXTENSION);
                    }
                    else
                    {
                        return null;
                    }
                    return mediaFile;
    }
}