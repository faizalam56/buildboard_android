package com.buildboard.modules.signup.contractor.helper;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.http.DataManager;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.io.File;

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
}
