package com.buildboard.modules.signup.contractor.businessinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.consumer.EditProfileActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.modules.signup.contractor.businessinfo.models.BusinessInfoData;
import com.buildboard.modules.signup.contractor.businessinfo.models.BusinessInfoRequest;
import com.buildboard.modules.signup.contractor.previouswork.models.SaveContractorImageRequest;
import com.buildboard.modules.signup.contractor.worktype.WorkTypeActivity;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.buildboard.utils.Utils.getImageUri;
import static com.buildboard.utils.Utils.resizeAndCompressImageBeforeSend;
import static com.buildboard.utils.Utils.selectImage;

public class SignUpContractorActivity extends AppCompatActivity implements AppConstant {

    private final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int PICK_IMAGE_CAMERA = 2001;
    private final int PICK_IMAGE_GALLERY = 2002;

    private String mProvider;
    private String mProviderId;
    private String mEmail;
    private LatLng addressLatLng;
    private String workingArea;
    private String mFirstNane;
    private String mLastName;
    private boolean mIsContractor;
    private Uri mSelectedImage;
    private Bitmap mBitmap;
    private String mResponsImageUrl;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindView(R.id.edit_business_name)
    BuildBoardEditText editBusinessName;
    @BindView(R.id.edit_business_address)
    BuildBoardTextView editBusinessAddress;
    @BindView(R.id.edit_first_name)
    BuildBoardEditText editPrincipalFirstName;
    @BindView(R.id.edit_last_name)
    BuildBoardEditText editPrincipalLastName;
    @BindView(R.id.edit_email)
    BuildBoardEditText editEmail;
    @BindView(R.id.edit_password)
    BuildBoardEditText editPassword;
    @BindView(R.id.edit_summary)
    BuildBoardEditText editSummary;
    @BindView(R.id.edit_phoneno)
    BuildBoardEditText editPhoneno;
    @BindView(R.id.edit_business_year)
    BuildBoardEditText editBusinessYear;

    @BindView(R.id.spinner_working_area)
    Spinner spinnerWorkingArea;

    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindView(R.id.text_business_name)
    BuildBoardTextView textBusinessName;
    @BindView(R.id.text_business_address)
    BuildBoardTextView textBusinessAddress;
    @BindView(R.id.text_first_name)
    BuildBoardTextView textFirstName;
    @BindView(R.id.text_last_name)
    BuildBoardTextView textLastName;
    @BindView(R.id.text_email)
    BuildBoardTextView textEmail;
    @BindView(R.id.text_password)
    BuildBoardTextView textPassword;
    @BindView(R.id.text_phoneno)
    BuildBoardTextView textPhone;
    @BindView(R.id.text_summary)
    BuildBoardTextView textSummary;
    @BindView(R.id.text_business_year)
    BuildBoardTextView textBusinessYear;
    @BindView(R.id.text_add_profile_picture)
    BuildBoardTextView textAddProfilePicture;

    @BindView(R.id.image_profile)
    ImageView imageProfile;

    @BindView(R.id.button_next)
    BuildBoardButton buttonNext;

    @BindString(R.string.sign_up)
    String stringSignUp;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindString(R.string.error_enter_first_name)
    String stringErrorFirstName;
    @BindString(R.string.error_enter_last_name)
    String stringErrorLastName;
    @BindString(R.string.error_invalid_email)
    String stringErrorInvalidEmail;
    @BindString(R.string.error_enter_email)
    String stringErrorEmail;
    @BindString(R.string.error_password)
    String stringErrorPasswordEmptyMsg;
    @BindString(R.string.error_enter_business_name)
    String stringErrorBusinessName;
    @BindString(R.string.error_enter_business_address)
    String stringErrorBusinessAddress;
    @BindString(R.string.error_enter_summary)
    String stringSummary;
    @BindString(R.string.error_enter_address)
    String stringErrorAddress;
    @BindString(R.string.error_enter_valid_phone_number)
    String stringErrorValidPhoneNumber;
    @BindString(R.string.error_enter_phone_number)
    String stringErrorPhoneNumber;
    @BindString(R.string.error_select_user_type)
    String stringErrorSelectUserType;
    @BindString(R.string.error_first_name_short)
    String stringErrorFirstnameTooShort;
    @BindString(R.string.error_incorrect_password_length)
    String stringErrorPasswordLength;
    @BindString(R.string.please_enter_a_valid_address)
    String stringEnterValidAddress;
    @BindString(R.string.error_working_area)
    String stringErrorWorkingArea;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;
    @BindString(R.string.error_enter_business_year)
    String stringErrorBusinessYear;
    @BindString(R.string.business_info)
    String stringBusinessInfo;
    @BindString(R.string.next)
    String stringNext;
    @BindString(R.string.save)
    String stringSave;
    @BindString(R.string.msg_success_businessinfo_update)
    String stringBusinessInfoSucess;

    @BindArray(R.array.array_working_area)
    String[] arrayWorkingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_contractor);
        ButterKnife.bind(this);

        mIsContractor = AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR);
        title.setText(mIsContractor ? stringBusinessInfo : stringSignUp);

        setAsteriskToText();
        setTermsServiceText();
        getIntentData();
        setViews();

        if (mIsContractor)
            getContractorBusinessInfo();
    }

    private void setViews() {
        textAddProfilePicture.setVisibility(mIsContractor ? View.VISIBLE : View.GONE);
        imageProfile.setVisibility(mIsContractor ? View.VISIBLE : View.GONE);

        textTermsOfService.setVisibility(mIsContractor ? View.GONE : View.VISIBLE);
        textPassword.setVisibility(mIsContractor ? View.GONE : View.VISIBLE);
        editPassword.setVisibility(mIsContractor ? View.GONE : View.VISIBLE);
        buttonNext.setText(mIsContractor ? stringSave : stringNext);
    }

    private void getContractorBusinessInfo() {
        if (!ConnectionDetector.isNetworkConnected(this)) {
            ConnectionDetector.createSnackBar(this, constraintRoot);

            return;
        }
        getBusinessInfo();
    }

    @OnClick(R.id.edit_business_address)
    void contractorAddressTapped() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_next)
    void nextTapped() {

        if (!ConnectionDetector.isNetworkConnected(this)) {
            ConnectionDetector.createSnackBar(this, constraintRoot);

            return;
        }
        String password;
        String businessName = editBusinessName.getText().toString();
        String businessAddress = editBusinessAddress.getText().toString();
        String principalFirstName = editPrincipalFirstName.getText().toString();
        String principalLastName = editPrincipalLastName.getText().toString();
        String email = editEmail.getText().toString();
        String summary = editSummary.getText().toString();
        String phoneNo = editPhoneno.getText().toString();
        String businessYear = editBusinessYear.getText().toString();

        if (mProviderId != null && mProvider != null) {
            password = "not_required"; //TODO remove hardcoded string
        } else {
            password = editPassword.getText().toString();
        }

        if (validateContractorFields(businessName, businessAddress, principalFirstName, principalLastName,
                email, password, workingArea, summary, phoneNo, businessYear)) {
            BusinessInfoRequest businessInfoRequest = getBusinessInfoRequest(businessName, businessAddress, principalFirstName, principalLastName,
                    email, password, workingArea, summary, phoneNo, businessYear);

            if (mIsContractor)
                updateProfileImage(mResponsImageUrl, businessInfoRequest);
            else
                saveBusinessInfo(businessInfoRequest);
        }
    }

    @OnClick({R.id.image_profile, R.id.text_add_profile_picture})
    void imageProfileTapped() {
        if (ConnectionDetector.isNetworkConnected(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionHelper permission = new PermissionHelper(this);
                if (!permission.checkPermission(permissions))
                    requestPermissions(permissions, REQUEST_PERMISSION_CODE);
                else
                    selectImage(this);
            } else
                selectImage(this);
        } else ConnectionDetector.createSnackBar(this, constraintRoot);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    getAddressLatLng(PlacePicker.getPlace(this, data));
                    break;

                case PICK_IMAGE_GALLERY:
                    mSelectedImage = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mSelectedImage);
                        imageProfile.setImageBitmap(mBitmap);
                        uploadImage(this, prepareFilePart(resizeAndCompressImageBeforeSend(this, Utils.getImagePath(this, mSelectedImage))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case PICK_IMAGE_CAMERA:
                    try {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            mBitmap = (Bitmap) extras.get("data");
                            mSelectedImage = getImageUri(this, mBitmap);
                            imageProfile.setImageBitmap(mBitmap);
                            uploadImage(this, prepareFilePart(resizeAndCompressImageBeforeSend(this, Utils.getImagePath(this, mSelectedImage))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private MultipartBody.Part prepareFilePart(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file[0]", file.getName(), requestFile);
    }

    private void getAddressLatLng(Place place) {
        editBusinessAddress.setText(place.getAddress());
        addressLatLng = place.getLatLng();
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

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_PROVIDER) && getIntent().hasExtra(INTENT_PROVIDER_ID) && getIntent().hasExtra(INTENT_EMAIL)) {
            mProvider = getIntent().getStringExtra(INTENT_PROVIDER);
            mProviderId = getIntent().getStringExtra(INTENT_PROVIDER_ID);
            mEmail = getIntent().getStringExtra(INTENT_EMAIL);
        }

        if (getIntent().hasExtra(INTENT_FIRST_NAME) && getIntent().hasExtra(INTENT_LAST_NAME)) {
            mFirstNane = getIntent().getStringExtra(INTENT_FIRST_NAME);
            mLastName = getIntent().getStringExtra(INTENT_LAST_NAME);

            editPrincipalFirstName.setText(mFirstNane != null ? mFirstNane : "");
            editPrincipalLastName.setText(mLastName != null ? mLastName : "");
        }

        boolean providerExist = mProvider != null && mProviderId != null;

        editPassword.setVisibility(providerExist ? View.GONE : View.VISIBLE);
        textPassword.setVisibility(providerExist ? View.GONE : View.VISIBLE);
        editEmail.setFocusable(!providerExist);
        editPassword.setFocusableInTouchMode(!providerExist);
        editPassword.setClickable(!providerExist);
        editPassword.setCursorVisible(!providerExist);

        if (mProvider != null && mProviderId != null)
            editEmail.setText(mEmail);

        populateWorkingArea();
    }

    private void populateWorkingArea() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayWorkingArea);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkingArea.setAdapter(adapter);

        spinnerWorkingArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                workingArea = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //TODO implement if needed
            }
        });
    }

    private BusinessInfoRequest getBusinessInfoRequest(String businessName, String businessAddress, String principalFirstName,
                                                       String principalLastName, String email, String password, String workingArea, String summary, String phoneNo, String businessYear) {
        BusinessInfoRequest businessInfoRequest = new BusinessInfoRequest();

        businessInfoRequest.setBusinessName(businessName);
        businessInfoRequest.setBusinessAddress(businessAddress);
        businessInfoRequest.setFirstName(principalFirstName);
        businessInfoRequest.setLastName(principalLastName);
        businessInfoRequest.setEmail(email);
        businessInfoRequest.setBusinessYear(Integer.parseInt(businessYear));
        if (!TextUtils.isEmpty(phoneNo))
            businessInfoRequest.setPhone(phoneNo);

        if (!password.equalsIgnoreCase("not_required")) // TODO hardcoded string
            businessInfoRequest.setPassword(password);

        if (!TextUtils.isEmpty(summary))
            businessInfoRequest.setSummary(summary);

        if (addressLatLng != null) {
            businessInfoRequest.setLatitude(String.valueOf(addressLatLng.latitude));
            businessInfoRequest.setLongitude(String.valueOf(addressLatLng.longitude));
        }

        String[] radius = workingArea.split("-");
        int minRadius = Integer.valueOf(radius[0].replaceAll("[\\D]", ""));
        int maxRadius = Integer.parseInt(radius[1].replaceAll("[\\D]", ""));
        businessInfoRequest.setMinAreaRadius(minRadius);
        businessInfoRequest.setMaxAreaRadius(maxRadius);

        if (!TextUtils.isEmpty(mProvider) && !TextUtils.isEmpty(mProviderId)) {
            businessInfoRequest.setProvider(mProvider);
            businessInfoRequest.setProviderId(mProviderId);
        }

        return businessInfoRequest;
    }

    private boolean validateContractorFields(String businessName, String businessAddress, String principalFirstName,
                                             String principalLastName, String email, String password, String workingArea, String summary, String phoneNo, String businessYear) {

        if (TextUtils.isEmpty(businessName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorBusinessName).show();
            return false;
        }

        if (TextUtils.isEmpty(businessAddress)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorBusinessAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(businessYear)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorBusinessYear);
            return false;
        } else if (Integer.parseInt(businessYear) == 0) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorBusinessYear);
            return false;
        }

        if (TextUtils.isEmpty(workingArea)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorWorkingArea).show();
            return false;
        }

        if (TextUtils.isEmpty(principalFirstName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstName);
            return false;
        } else if (principalFirstName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstnameTooShort);
            return false;
        }

        if (TextUtils.isEmpty(principalLastName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorLastName);
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorEmail).show();
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorInvalidEmail).show();
            return false;
        }

        if (!mIsContractor && !password.equalsIgnoreCase("not_required")) { //TODO: hardcoded string
            if (TextUtils.isEmpty(password)) {
                SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordEmptyMsg);
                return false;
            } else if (password.length() < 6) {
                SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordLength);
                return false;
            }
        }

        if (!TextUtils.isEmpty(phoneNo) && phoneNo.length() < 10) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorValidPhoneNumber).show();
            return false;
        }

        if (TextUtils.isEmpty(summary)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringSummary).show();
            return false;
        }

        return true;
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(SignUpContractorActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SignUpContractorActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    private void setAsteriskToText() {
        textBusinessName.setText(Utils.setStarToLabel(getString(R.string.business_name)));
        textBusinessAddress.setText(Utils.setStarToLabel(getString(R.string.business_address)));
        textFirstName.setText(Utils.setStarToLabel(getString(R.string.principal_first_name)));
        textLastName.setText(Utils.setStarToLabel(getString(R.string.principal_last_name)));
        textEmail.setText(Utils.setStarToLabel(getString(R.string.email)));
        textPassword.setText(Utils.setStarToLabel(getString(R.string.password)));
        textPhone.setText(Utils.setStarToLabel(getString(R.string.phone_no)));
        textSummary.setText(Utils.setStarToLabel(getString(R.string.summary)));
        textBusinessYear.setText(Utils.setStarToLabel(getString(R.string.year_in_business)));
    }

    private void setContractorDetails(BusinessInfoData businessInfoData) {
        Picasso.get().load(businessInfoData.getImage()).resize(80, 80).error(R.drawable.upload_profile_image).into(imageProfile);
        mResponsImageUrl = businessInfoData.getImage();
        editBusinessName.setText(businessInfoData.getBusinessName() != null ? businessInfoData.getBusinessName() : "");
        editBusinessAddress.setText(businessInfoData.getBusinessAddress() != null ? businessInfoData.getBusinessAddress() : "");
        editBusinessYear.setText(String.valueOf(businessInfoData.getBusinessYear()));
        editPrincipalFirstName.setText(businessInfoData.getFirstName() != null ? businessInfoData.getFirstName() : "");
        editPrincipalLastName.setText(businessInfoData.getLastName() != null ? businessInfoData.getLastName() : "");
        editEmail.setText(businessInfoData.getEmail() != null ? businessInfoData.getEmail() : "");
        editPhoneno.setText(businessInfoData.getPhone() != null ? businessInfoData.getPhone() : "");
        editSummary.setText(businessInfoData.getLastName() != null ? businessInfoData.getLastName() : "");
        addressLatLng = new LatLng(businessInfoData.getLatitude(), businessInfoData.getLongitude());
        for (int i = 0; i < spinnerWorkingArea.getCount(); i++) {
            if (spinnerWorkingArea.getItemAtPosition(i).toString().contains(String.valueOf(businessInfoData.getMinAreaRadius()))) {
                spinnerWorkingArea.setSelection(i);
            }
        }
    }

    private void saveBusinessInfo(BusinessInfoRequest businessInfoRequest) {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().saveBusinessInfo(this, businessInfoRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                BusinessInfoData businessInfoData = (BusinessInfoData) response;

                Intent intent = new Intent(SignUpContractorActivity.this, WorkTypeActivity.class);
                intent.putExtra(INTENT_WORK_TYPE_ID, businessInfoData.getUserId());
                startActivity(intent);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpContractorActivity.this, constraintRoot, error);
            }
        });
    }

    private void getBusinessInfo() {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().getBusinessInfo(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                BusinessInfoData businessInfoData = (BusinessInfoData) response;
                setContractorDetails(businessInfoData);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpContractorActivity.this, constraintRoot, error);
            }
        });
    }

    private void updateBusinessInfo(BusinessInfoRequest businessInfoRequest) {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().updateBusinessInfo(this, businessInfoRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                BusinessInfoData businessInfoData = (BusinessInfoData) response;
                Toast.makeText(SignUpContractorActivity.this, stringBusinessInfoSucess, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpContractorActivity.this, constraintRoot, error);
            }
        });
    }

    private void updateProfileImage(String imageUrl, final BusinessInfoRequest businessInfoRequest) {
        SaveContractorImageRequest contractorImageRequest = new SaveContractorImageRequest();
        contractorImageRequest.setImageUrl(imageUrl);
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().updateContractorImage(this, contractorImageRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                updateBusinessInfo(businessInfoRequest);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpContractorActivity.this, constraintRoot, error);
            }
        });
    }

    public void uploadImage(Activity activity, MultipartBody.Part image) {
        ProgressHelper.start(this, stringPleaseWait);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR) ? getString(R.string.contractor).toLowerCase() : getString(R.string.consumer).toLowerCase());
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), "image");
        DataManager.getInstance().uploadImage(activity, type, fileType, image, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                mResponsImageUrl = response.toString();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpContractorActivity.this, constraintRoot, error);
            }
        });
    }
}