package com.buildboard.modules.signup.imageupload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.imageupload.models.ImageUploadResponse;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUploadActivity extends AppCompatActivity implements AppConstant {

    private final int REQUEST_CODE = 2001;
    private Uri selectedImage;
    private final String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
    private final int REQUEST_PERMISSION_CODE = 300;

    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.button_save)
    Button buttonSave;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }
    }

    @OnClick(R.id.image_profile)
    void imageProfileTapped() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.button_save)
    void saveButtonTapped() {
        if (selectedImage == null) {
            Toast.makeText(this, R.string.please_select_image, Toast.LENGTH_SHORT).show();
            return;
        } else
            uploadImage(this, prepareFilePart("file[0]", Utils.getImagePath(this, selectedImage)));
    }

    public void uploadImage(Activity activity, MultipartBody.Part image) {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR) ?
                getString(R.string.contractor).toLowerCase() : getString(R.string.consumer).toLowerCase());
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), "image");
        DataManager.getInstance().uploadImage(activity, type, fileType, image, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                ImageUploadResponse imageUploadResponse = (ImageUploadResponse) response;
                Intent intent = new Intent();
                intent.putExtra(INTENT_IMAGE_URL, imageUploadResponse.getData().get(0));
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(ImageUploadActivity.this, constraintLayout, error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
        }
    }

    private MultipartBody.Part prepareFilePart(String partName, String imagePath) {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
