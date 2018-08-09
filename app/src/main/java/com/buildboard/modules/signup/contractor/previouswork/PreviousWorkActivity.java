package com.buildboard.modules.signup.contractor.previouswork;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.dialogs.AddProfilePhotoDialog;
import com.buildboard.http.DataManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.modules.signup.contractor.businessdocuments.BusinessDocumentsActivity;
import com.buildboard.modules.signup.contractor.businessdocuments.models.BusinessDocumentsResponse;
import com.buildboard.modules.signup.contractor.businessinfo.SignUpContractorActivity;
import com.buildboard.modules.signup.contractor.helper.ImageUploadHelper;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.interfaces.ISelectAttachment;
import com.buildboard.modules.signup.contractor.previouswork.adapters.PreviousWorkAdapter;
import com.buildboard.modules.signup.contractor.previouswork.adapters.TestimonialAdapter;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkData;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkRequest;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorks;
import com.buildboard.modules.signup.contractor.previouswork.models.SaveContractorImageRequest;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.buildboard.utils.Utils.resizeAndCompressImageBeforeSend;

public class PreviousWorkActivity extends AppCompatActivity implements AppConstant, ImageUploadHelper.IImageUrlCallback {

    private final String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final int REQUEST_CODE = 2001;

    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.previous_work)
    String stringPreviousWork;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;
    @BindString(R.string.text_msg_permission_required)
    String stringReadStoragePermission;
    @BindString(R.string.please_select_image)
    String stringSelectImage;

    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;

    @BindView(R.id.recycler_previous_work)
    RecyclerView recyclerPreviousWork;
    @BindView(R.id.recycler_testimonial)
    RecyclerView recyclerTestimonial;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    private String mUserId = "";
    private PreviousWorkAdapter mPreviousWorkAdapter;
    private TestimonialAdapter mTestimonialAdapter;
    private HashMap<Integer, ArrayList<PreviousWorkData>> mPreviousWorks = new HashMap<>();
    private HashMap<Integer, ArrayList<PreviousWorkData>> mTestimonials = new HashMap<>();
    private Uri selectedImage;
    private AddProfilePhotoDialog mAddProfilePhotoDialog;
    private String responsImageUrl;
    private ImageUploadHelper mImageUploadHelper;

    BottomSheetBehavior behavior;
    String mCurrentPhotoPath;
    private int mSelectedPosition;
    private boolean isAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_work);
        ButterKnife.bind(this);

        title.setText(stringPreviousWork);
        setTermsServiceText();
        getIntentData();

        addTestimonialData();
        addPreviousWorkData();
        setTestimonialAdapter();
        setPreviousWorkAdapter();
        mImageUploadHelper = ImageUploadHelper.getInstance();
        mAddProfilePhotoDialog = new AddProfilePhotoDialog();
        behavior = BottomSheetBehavior.from(bottomSheet);
    }

    @OnClick(R.id.button_next)
    void nextTapped() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            else
                showImageUploadDialog();
        } else
            showImageUploadDialog();
    }

    @OnClick(R.id.text_camera)
    void cameraTapped() {
        isAttachment = true;
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mCurrentPhotoPath = mImageUploadHelper.dispatchTakePictureIntent(this);
    }

    @OnClick(R.id.text_gallery)
    void galleryTapped() {
        isAttachment = true;
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.text_document)
    void documentTapped() {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mImageUploadHelper.showFileChooser(this);
    }

    @OnClick(R.id.text_cancel)
    void cancelTapped() {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_USER_ID))
            mUserId = getIntent().getStringExtra(INTENT_USER_ID);
    }

    private void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy_text));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpanTermsService, 34, 50, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(PreviousWorkActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    ClickableSpan clickableSpanPrivacyPolicy = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(PreviousWorkActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    private void storePrevWork() {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().storePrevWork(this, getPreviousWorkRequest(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                ArrayList<String> documentsResponse = (ArrayList<String>) response;
                showPrevworkSuccessDialog(documentsResponse.get(0));
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(PreviousWorkActivity.this, constraintRoot, error);
            }
        });
    }

    private PreviousWorkRequest getPreviousWorkRequest() {

        PreviousWorks previousWorks = new PreviousWorks();
        previousWorks.setPreviousWork(mPreviousWorks);
        previousWorks.setTestimonial(mTestimonials);

        PreviousWorkRequest previousWorkRequest = new PreviousWorkRequest();
        previousWorkRequest.setPreviousWorks(previousWorks);
        previousWorkRequest.setId(mUserId);

        return previousWorkRequest;
    }

    private void addTestimonialData() {

        ArrayList<PreviousWorkData> testimonialDetails = new ArrayList<>();
        PreviousWorkData nameInfo = new PreviousWorkData();
        nameInfo.setKey(KEY_NAME);
        nameInfo.setType(TYPE_TEXT);
        nameInfo.setValue(new ArrayList<String>());
        testimonialDetails.add(nameInfo);

        PreviousWorkData descriptionInfo = new PreviousWorkData();
        descriptionInfo.setKey(KEY_DESCRIPTION);
        descriptionInfo.setType(TYPE_TEXT);
        descriptionInfo.setValue(new ArrayList<String>());
        testimonialDetails.add(descriptionInfo);

        PreviousWorkData workPerformed = new PreviousWorkData();
        workPerformed.setKey(KEY_WORK_PERFORMED);
        workPerformed.setType(TYPE_TEXT);
        workPerformed.setValue(new ArrayList<String>());
        testimonialDetails.add(workPerformed);

        mTestimonials.put(mTestimonials.size() + 1, testimonialDetails);
    }

    private void addPreviousWorkData() {

        ArrayList<PreviousWorkData> previousWorkDetails = new ArrayList<>();
        PreviousWorkData descriptionInfo = new PreviousWorkData();
        descriptionInfo.setKey(KEY_DESCRIPTION);
        descriptionInfo.setType(TYPE_TEXT);
        descriptionInfo.setValue(new ArrayList<String>());
        previousWorkDetails.add(descriptionInfo);

        PreviousWorkData attachmentInfo = new PreviousWorkData();
        attachmentInfo.setKey(KEY_ATTACHMENT);
        attachmentInfo.setType(TYPE_MULTIPLE_ATTACHMENT);
        attachmentInfo.setValue(new ArrayList<String>());
        previousWorkDetails.add(attachmentInfo);

        mPreviousWorks.put(mPreviousWorks.size() + 1, previousWorkDetails);
    }

    private void setPreviousWorkAdapter() {
        mPreviousWorkAdapter = new PreviousWorkAdapter(this, mPreviousWorks, new IAddMoreCallback() {
            @Override
            public void addMore() {
                addPreviousWorkData();
                mPreviousWorkAdapter.notifyDataSetChanged();
            }
        }, new ISelectAttachment() {
            @Override
            public void selectAttachment(int position) {
                mSelectedPosition = position;
                attachmentTapped();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerPreviousWork.setLayoutManager(linearLayoutManager);
        recyclerPreviousWork.setAdapter(mPreviousWorkAdapter);
    }

    private void setTestimonialAdapter() {
        mTestimonialAdapter = new TestimonialAdapter(this, mTestimonials, new IAddMoreCallback() {
            @Override
            public void addMore() {
                addTestimonialData();
                mTestimonialAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerTestimonial.setLayoutManager(linearLayoutManager);
        recyclerTestimonial.setAdapter(mTestimonialAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE:
                    try {
                        if (isAttachment) {
                            if (ConnectionDetector.isNetworkConnected(this))
                                mImageUploadHelper.uploadImage(PreviousWorkActivity.this, mImageUploadHelper.prepareFilePart(resizeAndCompressImageBeforeSend(this, Utils.getImagePath(this, data.getData()))),
                                        constraintRoot, this);
                            else
                                ConnectionDetector.createSnackBar(this, constraintRoot);
                        } else {
                            selectedImage = data.getData();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                            mAddProfilePhotoDialog.imageProfile.setImageBitmap(bitmap);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_IMAGE_CAPTURE:
                    if (ConnectionDetector.isNetworkConnected(this)) {
                        if (mCurrentPhotoPath == null) return;

                        File path = new File(mCurrentPhotoPath);
                        if (!path.exists()) path.mkdirs();
                        File imageFile = new File(path, "image.jpg");

                        mImageUploadHelper.uploadImage(this, mImageUploadHelper.prepareFilePart(resizeAndCompressImageBeforeSend(this,
                                mCurrentPhotoPath)),
                                constraintRoot, this);
                    } else {
                        ConnectionDetector.createSnackBar(this, constraintRoot);
                    }
            }
        }
    }

    public void saveContractorImage() {
        SaveContractorImageRequest saveImageRequest = new SaveContractorImageRequest();
        saveImageRequest.setId(mUserId);
        saveImageRequest.setImageUrl(responsImageUrl);

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().saveContractorImage(this, saveImageRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                storePrevWork();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(PreviousWorkActivity.this, constraintRoot, error);
            }
        });
    }

    private void showImageUploadDialog() {
        mAddProfilePhotoDialog = new AddProfilePhotoDialog();

        mAddProfilePhotoDialog.showDialog(this, new AddProfilePhotoDialog.IAddProfileCallback() {
            @Override
            public void onImageSelection() {
                if (ConnectionDetector.isNetworkConnected(PreviousWorkActivity.this)) {
                    isAttachment = false;
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    ConnectionDetector.createSnackBar(PreviousWorkActivity.this, constraintRoot);
                }
            }

            @Override
            public void onSaveImage() {
                if (selectedImage == null) {
                    SnackBarFactory.createSnackBar(PreviousWorkActivity.this, constraintRoot, stringSelectImage);
                    return;
                }

                if (ConnectionDetector.isNetworkConnected(PreviousWorkActivity.this)) {
                    isAttachment = false;
                    mImageUploadHelper.uploadImage(PreviousWorkActivity.this, mImageUploadHelper.prepareFilePart(resizeAndCompressImageBeforeSend(PreviousWorkActivity.this, Utils.getImagePath(PreviousWorkActivity.this, selectedImage))),
                            constraintRoot, PreviousWorkActivity.this);
                } else {
                    ConnectionDetector.createSnackBar(PreviousWorkActivity.this, constraintRoot);
                }
            }
        });
        if (selectedImage != null) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                mAddProfilePhotoDialog.imageProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    SnackBarFactory.createSnackBar(PreviousWorkActivity.this, constraintRoot, stringReadStoragePermission);
                }
                return;
            }
        }
    }

    @Override
    public void imageUrl(String url) {
        responsImageUrl = url;

        if (isAttachment) {
            mPreviousWorks.get(mSelectedPosition).get(1).getValue().add(responsImageUrl);
            mPreviousWorkAdapter.notifyDataSetChanged();
        } else saveContractorImage();
    }

    private void attachmentTapped() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            else
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void showPrevworkSuccessDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PreviousWorkActivity.this);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PreviousWorkActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
