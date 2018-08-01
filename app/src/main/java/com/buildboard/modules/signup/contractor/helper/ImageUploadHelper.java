package com.buildboard.modules.signup.contractor.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.http.DataManager;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUploadHelper implements AppConstant {

    private static class ImageUploadHelperSingleton {
        private static final ImageUploadHelper INSTANCE = new ImageUploadHelper();
    }

    public static ImageUploadHelper getInstance() {
        return ImageUploadHelper.ImageUploadHelperSingleton.INSTANCE;
    }

    public MultipartBody.Part prepareFilePart(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file[0]", file.getName(), requestFile);
    }

    public void uploadImage(final Activity activity, MultipartBody.Part image, final ConstraintLayout constraintRoot, final IImageUrlCallback iImageUrlCallback) {
        ProgressHelper.start(activity, activity.getString(R.string.msg_please_wait));
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getAppPreference(activity).getBoolean(IS_CONTRACTOR) ? activity.getString(R.string.contractor).toLowerCase() : activity.getString(R.string.consumer).toLowerCase());
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), "image");
        DataManager.getInstance().uploadImage(activity, type, fileType, image, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                iImageUrlCallback.imageUrl(response.toString());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(activity, constraintRoot, error);
            }
        });
    }

    public interface IImageUrlCallback {
        void imageUrl(String url);
    }

    /*public void dispatchTakePictureIntent(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/

    public String dispatchTakePictureIntent(Activity activity) {
        String currentPhotoPath = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
                currentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "com.buildboard.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                return currentPhotoPath;
            }
        }

        return null;
    }

    private File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
}
