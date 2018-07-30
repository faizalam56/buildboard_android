package com.buildboard.modules.signup.contractor.previouswork;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.Manifest;
import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.dialogs.AddProfilePhotoDialog;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.EditProfileActivity;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.previouswork.adapters.PreviousWorkAdapter;
import com.buildboard.modules.signup.contractor.previouswork.adapters.TestimonialAdapter;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkData;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkRequest;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorks;
import com.buildboard.modules.signup.contractor.previouswork.models.SaveContractorImageRequest;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.google.android.gms.location.places.ui.PlacePicker;

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

public class PreviousWorkActivity extends AppCompatActivity implements AppConstant {

    private final int REQUEST_CODE = 2001;

    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.previous_work)
    String stringPreviousWork;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindString(R.string.please_wait)
    String stringPleaseWait;

    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;

    @BindView(R.id.recycler_previous_work)
    RecyclerView recyclerPreviousWork;
    @BindView(R.id.recycler_testimonial)
    RecyclerView recyclerTestimonial;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    private String mUserId = "";
    private PreviousWorkAdapter mPreviousWorkAdapter;
    private TestimonialAdapter mTestimonialAdapter;
    private HashMap<Integer, ArrayList<PreviousWorkData>> mPreviousWorks = new HashMap<>();
    private HashMap<Integer, ArrayList<DocumentData>> mTestimonials = new HashMap<>();
    private Uri selectedImage;
    private AddProfilePhotoDialog mAddProfilePhotoDialog;
    private String responsImageUrl;

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
    }

    @OnClick(R.id.button_next)
    void nextTapped() {
        mAddProfilePhotoDialog = new AddProfilePhotoDialog();
        mAddProfilePhotoDialog.showDialog(this, new AddProfilePhotoDialog.IAddProfileCallback() {
            @Override
            public void onImageSelection() {
                if (ConnectionDetector.isNetworkConnected(PreviousWorkActivity.this)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    ConnectionDetector.createSnackBar(PreviousWorkActivity.this, constraintRoot);
                }
            }

            @Override
            public void onSaveImage() {
                if (ConnectionDetector.isNetworkConnected(PreviousWorkActivity.this)) {
                    uploadImage(PreviousWorkActivity.this, prepareFilePart(resizeAndCompressImageBeforeSend(PreviousWorkActivity.this, Utils.getImagePath(PreviousWorkActivity.this, selectedImage))));
                } else {
                    ConnectionDetector.createSnackBar(PreviousWorkActivity.this, constraintRoot);
                }
            }
        });
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
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
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
//        previousWorkRequest.setId("6e1fff70-9199-11e8-8aa5-9d8fa17e7dc6");

        return previousWorkRequest;
    }

    private void addTestimonialData() {

        ArrayList<DocumentData> testimonialDetails = new ArrayList<>();
        DocumentData nameInfo = new DocumentData();
        nameInfo.setKey(KEY_NAME);
        nameInfo.setType(TYPE_TEXT);
        nameInfo.setValue("");
        testimonialDetails.add(nameInfo);

        DocumentData descriptionInfo = new DocumentData();
        descriptionInfo.setKey(KEY_DESCRIPTION);
        descriptionInfo.setType(TYPE_TEXT);
        descriptionInfo.setValue("");
        testimonialDetails.add(descriptionInfo);

        DocumentData workPerformed = new DocumentData();
        workPerformed.setKey(KEY_WORK_PERFORMED);
        workPerformed.setType(TYPE_TEXT);
        workPerformed.setValue("");
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

        if (data == null) return;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE:
//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    mAddProfilePhotoDialog.imageProfile.setImageBitmap(imageBitmap);

                    selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        mAddProfilePhotoDialog.imageProfile.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void uploadImage(Activity activity, MultipartBody.Part image) {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR) ? getString(R.string.contractor).toLowerCase() : getString(R.string.consumer).toLowerCase());
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), "image");
        DataManager.getInstance().uploadImage(activity, type, fileType, image, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                responsImageUrl = response.toString();
                saveContractorImage();
            }
            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(PreviousWorkActivity.this, constraintRoot, error);
            }
        });
    }

    public void saveContractorImage() {
        SaveContractorImageRequest saveImageRequest = new SaveContractorImageRequest();
        saveImageRequest.setId(mUserId);
//        saveImageRequest.setId("6e1fff70-9199-11e8-8aa5-9d8fa17e7dc6");
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

    private MultipartBody.Part prepareFilePart(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file[0]", file.getName(), requestFile);
    }
}
